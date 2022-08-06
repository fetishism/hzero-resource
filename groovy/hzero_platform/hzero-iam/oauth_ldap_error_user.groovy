package script.db

databaseChangeLog(logicalFilePath: 'script/db/oauth_ldap_error_user.groovy') {
    def weight = 1
    if(helper.isSqlServer()){
        weight = 2
    } else if(helper.isOracle()){
        weight = 3
    }
    changeSet(author: 'superleader8@gmail.com', id: '2019-01-22-oauth-ldap-error-user') {
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'oauth_ldap_error_user_s', startValue:"1")
        }
        createTable(tableName: "oauth_ldap_error_user") {
            column(name: 'id', type: 'BIGINT', autoIncrement: true, remarks: '表ID，主键，供其他表做外键， bigint、单表时自增、步长为 1') {
                constraints(primaryKey: true, primaryKeyName: 'PK_OAUTH_LDAP_ERROR_USER')
            }
            column(name: 'ldap_history_id', type: 'BIGINT', remarks: 'ldap同步历史id') {
                constraints(nullable: false)
            }
            column(name: 'uuid', type: "VARCHAR(128)", remarks: 'ldap对象的唯一标识，可以根据此标识到ldap server查询详细信息') {
                constraints(nullable: false)
            }
            column(name: 'login_name', type: "VARCHAR(128)", remarks: '用户登录名')
            column(name: 'email', type: "VARCHAR(128)", remarks: '用户邮箱')
            column(name: 'real_name', type: "VARCHAR(" + weight * 128  + ")", remarks: '真实姓名')
            column(name: "phone", type: "VARCHAR(128)", remarks: '手机号')
            column(name: "cause", type: "VARCHAR(" + weight * 128  + ")", remarks: '失败原因')

            column(name: "object_version_number", type: "BIGINT", defaultValue: "1") {
                constraints(nullable: true)
            }
            column(name: "created_by", type: "BIGINT", defaultValue: "0") {
                constraints(nullable: true)
            }
            column(name: "creation_date", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "last_updated_by", type: "BIGINT", defaultValue: "0") {
                constraints(nullable: true)
            }
            column(name: "last_update_date", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
    }
    changeSet(author: "hzero@hand-china.com", id: "2020-06-11-oauth_ldap_error_user") {
        addColumn(tableName: 'oauth_ldap_error_user') {
            column(name: "tenant_id", type: "bigint", defaultValue: "0", remarks: "租户ID,hpfm_tenant.tenant_id") {
                constraints(nullable: "false")
            }
        }
    }
}