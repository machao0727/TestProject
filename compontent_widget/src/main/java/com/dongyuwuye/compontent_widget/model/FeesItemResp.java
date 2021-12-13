package com.dongyuwuye.compontent_widget.model;

/**
 * create by：mc on 2019/12/10 14:51
 * email:
 */
public class FeesItemResp {
    private String CostID;
    private String CostName;
    private String SysCostSign;
    private String StanID;
    private String StanName;
    private String StanAmount;

    public String getCostID() {
        return CostID;
    }

    public void setCostID(String costID) {
        CostID = costID;
    }

    public String getCostName() {
        return CostName;
    }

    public void setCostName(String costName) {
        CostName = costName;
    }

    public String getSysCostSign() {
        return SysCostSign;
    }

    public void setSysCostSign(String sysCostSign) {
        SysCostSign = sysCostSign;
    }

    public String getStanID() {
        return StanID;
    }

    public void setStanID(String stanID) {
        this.StanID = stanID;
    }

    public String getStanName() {
        return StanName;
    }

    public void setStanName(String stanName) {
        StanName = stanName;
    }

    public String getStanAmount() {
        return StanAmount;
    }

    public void setStanAmount(String stanAmount) {
        StanAmount = stanAmount;
    }
}
