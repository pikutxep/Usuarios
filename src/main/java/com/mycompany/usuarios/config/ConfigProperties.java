/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usuarios.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author hescu
 */
@Configuration
@ConfigurationProperties(prefix = "persistence")
public class ConfigProperties {

    private String type;
    private Datasource h2;
    private Datasource mysql;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Datasource getH2() {
        return h2;
    }

    public void setH2(Datasource h2) {
        this.h2 = h2;
    }

    public Datasource getMysql() {
        return mysql;
    }

    public void setMysql(Datasource mysql) {
        this.mysql = mysql;
    }

}
