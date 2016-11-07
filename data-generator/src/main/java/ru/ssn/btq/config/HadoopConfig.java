package ru.ssn.btq.config;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.io.IOException;


@Configuration
public class HadoopConfig {

    @Resource
    private Environment env;

    @Bean
    public org.apache.hadoop.conf.Configuration configuration() {
        org.apache.hadoop.conf.Configuration hadoopConfig = new org.apache.hadoop.conf.Configuration();

        hadoopConfig.addResource(new Path("/etc/spark/conf/yarn-conf/core-site.xml"));
        hadoopConfig.addResource(new Path("/etc/spark/conf/yarn-conf/hdfs-site.xml"));
        hadoopConfig.addResource(new Path("/etc/spark/conf/yarn-conf/yarn-site.xml"));

//        CONFIGURATION.set("spark.home", "/opt/cloudera/parcels/CDH/lib/spark");
        hadoopConfig.set("fs.hdfs.impl",
                org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
        );
        hadoopConfig.set("fs.file.impl",
                org.apache.hadoop.fs.LocalFileSystem.class.getName()
        );

        return hadoopConfig;
    }

    @Bean
    public FileSystem fileSystem() {
        try {
            return FileSystem.get(configuration());
        } catch (IOException e) {
            throw new RuntimeException("Cannot instatniate file system", e);
        }
    }

}
