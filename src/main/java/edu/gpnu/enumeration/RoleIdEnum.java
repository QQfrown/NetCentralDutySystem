package edu.gpnu.enumeration;

public enum RoleIdEnum {
    TEAMLEADER("10000"),COMMONNETWORKMANAGEMENT("10001");


    public   String  RoleID;

    RoleIdEnum(String s) {
        this.RoleID=s;
    }

    public String getRoleID(){
        return RoleID;
    }
}
