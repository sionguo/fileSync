package cn.guoxy.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author GuoXiaoyong
 */
@Data
public class Member implements Serializable {

    private String ip;
    private Integer port;
//    private String id;

}
