package com.maddyjace.worldrulesmanage.util;

import com.maddyjace.worldrulesmanage.WorldRulesManage;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.maddyjace.worldrulesmanage.util.Ref.wdl;

// 该类用于监听文件发生变化时
public class AutoReload {

    private final File folder;
    private final long intervalMillis;
    private final String[] suffixFilters;
    private final boolean recursive;

    private FileAlterationMonitor monitor;

    public AutoReload(String folderPath, long intervalMillis, boolean recursive, String... suffixFilters) {
        this.folder = new File(folderPath);
        this.intervalMillis = intervalMillis;
        this.suffixFilters = suffixFilters == null ? new String[0] : suffixFilters;
        this.recursive = recursive;
    }

    // 初始化监听
    public void start() {

        // 文件过滤器（后缀）
        IOFileFilter fileFilter;
        if (suffixFilters.length == 0) {
            fileFilter = TrueFileFilter.TRUE;
        } else {
            List<IOFileFilter> filters = new ArrayList<>();
            for (String suffix : suffixFilters) {
                filters.add(FileFilterUtils.suffixFileFilter(suffix.toLowerCase()));
            }
            fileFilter = FileFilterUtils.or(filters.toArray(new IOFileFilter[0]));
        }

        // 组合过滤器
        IOFileFilter combinedFilter;

        if (recursive) {
            // 允许进入子目录（非隐藏目录）
            IOFileFilter dirFilter = FileFilterUtils.and(
                    FileFilterUtils.directoryFileFilter(),
                    HiddenFileFilter.VISIBLE
            );
            combinedFilter = FileFilterUtils.or(dirFilter, fileFilter);
        } else {
            // 不递归：只监听根目录文件
            combinedFilter = fileFilter;
        }

        // 创建监听
        FileAlterationObserver observer = getFileAlterationObserver(combinedFilter);
        monitor = new FileAlterationMonitor(intervalMillis, observer);
        try {
            monitor.start();
        } catch (Exception e) {
            String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
            info(Language.Get.translate(Language.getServerLanguage(), "autoReloadError", pluginName));
        }
    }

    // 监听(创建 修改 删除)
    private FileAlterationObserver getFileAlterationObserver(IOFileFilter combinedFilter) {
        FileAlterationObserver observer = new FileAlterationObserver(folder, combinedFilter);
        observer.addListener(new FileAlterationListenerAdaptor() {

            // 创建
            @Override
            public void onFileCreate(File file) {
                // 检查 file 父级是否为globalRules文件夹中的文件
                if (file.getParentFile().getName().equalsIgnoreCase("globalRules")) {
                    String noFileExtension = WorldUtil.removeSuffix(file.getName());
                    YamlConfiguration YAML = YamlConfiguration.loadConfiguration(file);
                    wdl.getGlobalData().put(noFileExtension, wdl.parseYAMLData(noFileExtension, YAML, false));
                    Bukkit.getScheduler().runTask(Get.plugin(), () -> {
                        String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
                        info(Language.Get.translate(Language.getServerLanguage(), "autoReloadCreate", pluginName, file.getName()));
                    });
                    return;
                }
                // 检查 file 父级是否为localRules文件夹中的文件
                if (file.getParentFile().getName().equalsIgnoreCase("localRules")) {
                    String noFileExtension = WorldUtil.removeSuffix(file.getName());
                    YamlConfiguration YAML = YamlConfiguration.loadConfiguration(file);
                    wdl.getLocalData().put(noFileExtension, wdl.parseYAMLData(noFileExtension, YAML, false));
                    Bukkit.getScheduler().runTask(Get.plugin(), () -> {
                        String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
                        info(Language.Get.translate(Language.getServerLanguage(), "autoReloadCreate", pluginName, file.getName()));
                    });
                    return;
                }
                // 检查 file 父级是否为language文件夹中的文件
                if (file.getParentFile().getName().equalsIgnoreCase("language")) {
                    String noFileExtension = WorldUtil.removeSuffix(file.getName());
                    Language.Get.languages.remove(noFileExtension);
                    Language.Get.parseYAMLData(file.getName(), noFileExtension, YamlConfiguration.loadConfiguration(file));
                    Bukkit.getScheduler().runTask(Get.plugin(), () -> {
                        String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
                        info(Language.Get.translate(Language.getServerLanguage(), "reload", pluginName, file.getName()));
                    });
                    return;
                }
                // 检查 file 父级是否为WorldRulesManage文件夹中的是否为 config 文件
                if (file.getParentFile().getName().equalsIgnoreCase("WorldRulesManage")) {
                    String noFileExtension = WorldUtil.removeSuffix(file.getName());
                    if (noFileExtension != null && noFileExtension.equalsIgnoreCase("config")) {
                        WorldRulesManage.INSTANCE.onDisable();
                        WorldRulesManage.INSTANCE.onEnable();
                        Bukkit.getScheduler().runTask(Get.plugin(), () -> {
                            String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
                            info(Language.Get.translate(Language.getServerLanguage(), "autoReloadRestart", pluginName, file.getName()));
                        });
                    }
                }
            }

            // 修改
            @Override
            public void onFileChange(File file) {
                // 检查 file 父级是否为globalRules文件夹中的文件
                if (file.getParentFile().getName().equalsIgnoreCase("globalRules")) {
                    String noFileExtension = WorldUtil.removeSuffix(file.getName());
                    YamlConfiguration YAML = YamlConfiguration.loadConfiguration(file);
                    wdl.getGlobalData().put(noFileExtension, wdl.parseYAMLData(noFileExtension, YAML, false));
                    Bukkit.getScheduler().runTask(Get.plugin(), () -> {
                        String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
                        info(Language.Get.translate(Language.getServerLanguage(), "autoReloadChange", pluginName, file.getName()));
                    });
                    return;
                }
                // 检查 file 父级是否为localRules文件夹中的文件
                if (file.getParentFile().getName().equalsIgnoreCase("localRules")) {
                    String noFileExtension = WorldUtil.removeSuffix(file.getName());
                    YamlConfiguration YAML = YamlConfiguration.loadConfiguration(file);
                    wdl.getLocalData().put(noFileExtension, wdl.parseYAMLData(noFileExtension, YAML, false));
                    Bukkit.getScheduler().runTask(Get.plugin(), () -> {
                        String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
                        info(Language.Get.translate(Language.getServerLanguage(), "autoReloadChange", pluginName, file.getName()));
                    });
                    return;
                }
                // 检查 file 父级是否为language文件夹中的文件
                if (file.getParentFile().getName().equalsIgnoreCase("language")) {
                    String noFileExtension = WorldUtil.removeSuffix(file.getName());
                    Language.Get.languages.remove(noFileExtension);
                    Language.Get.parseYAMLData(file.getName(), noFileExtension, YamlConfiguration.loadConfiguration(file));
                    Bukkit.getScheduler().runTask(Get.plugin(), () -> {
                        String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
                        info(Language.Get.translate(Language.getServerLanguage(), "autoReloadChange", pluginName, file.getName()));
                    });
                    return;
                }
                // 检查 file 父级是否为WorldRulesManage文件夹中的是否为 config 文件
                if (file.getParentFile().getName().equalsIgnoreCase("WorldRulesManage")) {
                    String noFileExtension = WorldUtil.removeSuffix(file.getName());
                    if (noFileExtension != null && noFileExtension.equalsIgnoreCase("config")) {
                        WorldRulesManage.INSTANCE.onDisable();
                        WorldRulesManage.INSTANCE.onEnable();
                        Bukkit.getScheduler().runTask(Get.plugin(), () -> {
                            String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
                            info(Language.Get.translate(Language.getServerLanguage(), "autoReloadRestart", pluginName, file.getName()));
                        });
                    }
                }
            }

            // 删除
            @Override
            public void onFileDelete(File file) {
                // 检查 file 父级是否为globalRules文件夹中的文件
                if (file.getParentFile().getName().equalsIgnoreCase("globalRules")) {
                    String noFileExtension = WorldUtil.removeSuffix(file.getName());
                    wdl.getGlobalData().remove(noFileExtension);
                    Bukkit.getScheduler().runTask(Get.plugin(), () -> {
                        String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
                        info(Language.Get.translate(Language.getServerLanguage(), "autoReloadDelete", pluginName, file.getName()));
                    });
                    return;
                }
                // 检查 file 父级是否为localRules文件夹中的文件
                if (file.getParentFile().getName().equalsIgnoreCase("localRules")) {
                    String noFileExtension = WorldUtil.removeSuffix(file.getName());
                    wdl.getLocalData().remove(noFileExtension);
                    Bukkit.getScheduler().runTask(Get.plugin(), () -> {
                        String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
                        info(Language.Get.translate(Language.getServerLanguage(), "autoReloadDelete", pluginName, file.getName()));
                    });
                    return;
                }
                // 检查 file 父级是否为language文件夹中的文件
                if (file.getParentFile().getName().equalsIgnoreCase("language")) {
                    String noFileExtension = WorldUtil.removeSuffix(file.getName().toLowerCase());
                    Language.Get.languages.remove(noFileExtension);
                    Bukkit.getScheduler().runTask(Get.plugin(), () -> {
                        String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
                        info(Language.Get.translate(Language.getServerLanguage(), "autoReloadDelete", pluginName, file.getName()));
                    });
                    return;
                }
                // 检查 file 父级是否为WorldRulesManage文件夹中的是否为 config 文件
                if (file.getParentFile().getName().equalsIgnoreCase("WorldRulesManage")) {
                    String noFileExtension = WorldUtil.removeSuffix(file.getName());
                    if (noFileExtension != null && noFileExtension.equalsIgnoreCase("config")) {
                        WorldRulesManage.INSTANCE.onDisable();
                        WorldRulesManage.INSTANCE.onEnable();
                        Bukkit.getScheduler().runTask(Get.plugin(), () -> {
                            String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
                            info(Language.Get.translate(Language.getServerLanguage(), "reload", pluginName, file.getName()));
                        });
                    }
                }

            }
        });
        return observer;
    }

    // 关闭监听
    public void stop() {
        if (monitor != null) {
            try {
                monitor.stop();
            } catch (Exception ignored) {}
        }
    }

    /* ------------------------------------------------------------------------------------------------------------- */


    /** 向控制台发送 "创建", "修改", "删除" 文件的信息*/
    private static void info(String fileName) {
        Get.plugin().getLogger().info(fileName);
        // Bukkit.getConsoleSender().sendMessage(fileName);
    }
}
