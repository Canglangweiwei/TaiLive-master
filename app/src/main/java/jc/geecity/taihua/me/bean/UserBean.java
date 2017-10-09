package jc.geecity.taihua.me.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * <p>
 * 功能描述：登录信息类
 * </p>
 * Created by Liu Peng on 2015/7/24.
 */
@SuppressWarnings("ALL")
public class UserBean extends DataSupport implements Serializable {

    private int userid;              // 用户id
    private int departId;           // 部门id
    private String bumenName;       // 部门
    private String realName;        // 用户真名
    private String userName;        // 用户名
    private String password;        // 密码
    private String position;        // 职位
    private String pic;             // 用户头像
    private String areaId;          // 小区id
    private String glc;             // 管理处
    private String glc_name;        // 管理处
    private int auth;               // 用户权限
    private String copyright;       // 版权
    private String company;         // 所属物业公司
    private String oaServer;        // oa服务器地址
    private String server;
    private String oaMethod;        // oa接口方法名
    private String hx_header;       // 环信账号头
    private String zhaohu;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getDepartId() {
        return departId;
    }

    public void setDepartId(int departId) {
        this.departId = departId;
    }

    public String getBumenName() {
        return bumenName;
    }

    public void setBumenName(String bumenName) {
        this.bumenName = bumenName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getGlc() {
        return glc;
    }

    public void setGlc(String glc) {
        this.glc = glc;
    }

    public String getGlc_name() {
        return glc_name;
    }

    public void setGlc_name(String glc_name) {
        this.glc_name = glc_name;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOaServer() {
        return oaServer;
    }

    public void setOaServer(String oaServer) {
        this.oaServer = oaServer;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getOaMethod() {
        return oaMethod;
    }

    public void setOaMethod(String oaMethod) {
        this.oaMethod = oaMethod;
    }

    /**
     * 绑定信息(用于消息推送)
     *
     * @return
     */
    public String getBindUserinfo() {
        return company + userName;
    }

    public String getHx_header() {
        return hx_header;
    }

    public void setHx_header(String hx_header) {
        this.hx_header = hx_header;
    }

    public String getZhaohu() {
        return zhaohu;
    }

    public void setZhaohu(String zhaohu) {
        this.zhaohu = zhaohu;
    }

    public UserBean() {
        super();
    }
}
