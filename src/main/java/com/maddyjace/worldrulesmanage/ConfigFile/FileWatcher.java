package com.maddyjace.worldrulesmanage.ConfigFile;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

// 该类用于监听文件发生变化时
public class FileWatcher {

    private final File folder;
    private final String suffixFilter;

    private final long intervalMillis;

    private FileAlterationMonitor monitor;

    public FileWatcher(String folderPath, String suffixFilter, long intervalMillis) {
        this.folder = new File(folderPath);
        this.suffixFilter = suffixFilter == null ? "" : suffixFilter.toLowerCase();
        this.intervalMillis = intervalMillis;
    }

    // 初始化监听
    public void start() throws Exception {
        // 过滤器(只允许非隐藏的目录)
        IOFileFilter dirFilter = FileFilterUtils.and(
                FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
        IOFileFilter fileFilter = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                suffixFilter.isEmpty() ? TrueFileFilter.TRUE : FileFilterUtils.suffixFileFilter(suffixFilter)
        );
        // 过滤器(只允许YML文件)
        IOFileFilter combinedFilter = FileFilterUtils.or(dirFilter, fileFilter);

        // 创建观察者，带过滤器和递归标志
        FileAlterationObserver observer = getFileAlterationObserver(combinedFilter);
        monitor = new FileAlterationMonitor(intervalMillis, observer);
        monitor.start();

        // System.out.println("文件监听启动，监控目录：" + folder.getAbsolutePath());
    }


    // 监听(创建 修改 删除)
    private FileAlterationObserver getFileAlterationObserver(IOFileFilter combinedFilter) {

        FileAlterationObserver observer = new FileAlterationObserver(folder, combinedFilter);

        // 添加监听器，监听创建、修改、删除事件，统一调用私有处理方法
        observer.addListener(new FileAlterationListenerAdaptor() {
            // 创建
            @Override
            public void onFileCreate(File file) {
                // onFileChanged(file);
            }

            // 修改
            @Override
            public void onFileChange(File file) {
                onFileChanged(file);
            }

            // 删除
            @Override
            public void onFileDelete(File file) {
                // onFileChanged(file);
            }
        });

        return observer;
    }

    // 关闭监听
    public void stop() throws Exception {
        if (monitor != null) {
            monitor.stop();
        }
    }


    // 处理逻辑方法
    private void onFileChanged(File file) {
        if(file.getName().equals("world.yml")) {
            WorldFile.INSTANCE.reload();
            MessageFile.CmdReloadInfo(); // 向控制台发送信息
        }

        if(file.getName().equals("message.yml")) {
            MessageFile.INSTANCE.reload();
            MessageFile.CmdReloadInfo(); // 向控制台发送信息
        }

        if(file.getName().equals("config.yml")) {
            ConfigFile.INSTANCE.reload();
            MessageFile.CmdReloadInfo(); // 向控制台发送信息
        }

        if(file.getName().equals("radius.yml")) {
            RadiusFile.INSTANCE.reload();
            MessageFile.CmdReloadInfo(); // 向控制台发送信息
        }
    }

}
