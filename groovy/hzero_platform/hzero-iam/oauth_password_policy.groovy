package script.db

databaseChangeLog(logicalFilePath: 'script/db/oauth_password_policy.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-oauth_password_policy") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'oauth_password_policy_s', startValue:"1")
        }
        createTable(tableName: "oauth_password_policy", remarks: "") {
            column(name: "id", type: "bigint", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
            column(name: "code", type: "varchar(" + 64 * weight + ")",  remarks: "密码策略标识")  {constraints(nullable:"false")}  
            column(name: "name", type: "varchar(" + 64 * weight + ")",  remarks: "密码策略名")  {constraints(nullable:"false")}  
            column(name: "organization_id", type: "bigint",  remarks: "所属的组织id")  {constraints(nullable:"false")}  
            column(name: "original_password", type: "varchar(" + 64 * weight + ")",  remarks: "新建用户初始密码")   
            column(name: "min_length", type: "int",   defaultValue:"6",   remarks: "密码最小长度")   
            column(name: "max_length", type: "int",   defaultValue:"30",   remarks: "密码最大长度")   
            column(name: "max_error_time", type: "int",   defaultValue:"10",   remarks: "密码输入最大错误次数")   
            column(name: "digits_count", type: "int",  remarks: "密码数字的数量")   
            column(name: "lowercase_count", type: "int",  remarks: "密码小写字母数量")   
            column(name: "uppercase_count", type: "int",  remarks: "密码大写字母数量")   
            column(name: "special_char_count", type: "int",  remarks: "密码特殊字符数量")   
            column(name: "not_username", type: "tinyint",   defaultValue:"0",   remarks: "密码可否和与用户名相同")  {constraints(nullable:"false")}  
            column(name: "regular_expression", type: "varchar(" + 128 * weight + ")",  remarks: "密码匹配的正则表达式")   
            column(name: "not_recent_count", type: "int",  remarks: "是否可以修改为最近使用过的密码")   
            column(name: "enable_password", type: "tinyint",   defaultValue:"0",   remarks: "开启密码策略")  {constraints(nullable:"false")}  
            column(name: "enable_security", type: "tinyint",   defaultValue:"0",   remarks: "开启登录安全策略")  {constraints(nullable:"false")}  
            column(name: "enable_lock", type: "tinyint",   defaultValue:"0",   remarks: "是否锁定")  {constraints(nullable:"false")}  
            column(name: "locked_expire_time", type: "int",   defaultValue:"86400",   remarks: "锁定时长(s)")  {constraints(nullable:"false")}  
            column(name: "enable_captcha", type: "tinyint",   defaultValue:"1",   remarks: "启用验证码")  {constraints(nullable:"false")}  
            column(name: "max_check_captcha", type: "int",   defaultValue:"5",   remarks: "密码错误多少次需要验证码")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "")   
            column(name: "created_by", type: "bigint",   defaultValue:"0",   remarks: "")   
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
            column(name: "last_updated_by", type: "bigint",   defaultValue:"0",   remarks: "")   
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

        }

        addUniqueConstraint(columnNames:"code",tableName:"oauth_password_policy",constraintName: "oauth_password_policy_u1")
        addUniqueConstraint(columnNames:"organization_id",tableName:"oauth_password_policy",constraintName: "oauth_password_policy_u2")
    }

    changeSet(author: "hzero@hand-china.com", id: "2019-08-27-oauth_password_policy"){
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        addColumn(tableName: 'oauth_password_policy') {
            column(name: "enable_web_multiple_login", type: "tinyint", defaultValue: "1", remarks: "Web端允许多处登录，1为启用，0为禁用")
        }
        addColumn(tableName: 'oauth_password_policy') {
            column(name: "enable_app_multiple_login", type: "tinyint", defaultValue: "1", remarks: "移动端允许多处登录，1为启用，0为禁用")
        }
        addColumn(tableName: 'oauth_password_policy') {
            column(name: "password_update_rate", type: "bigint", defaultValue: "0", remarks: "密码更新频率，单位：天")
        }
    }
	
	changeSet(author: "hzero@hand-china.com", id: "2019-10-10-oauth_password_policy"){
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        addColumn(tableName: 'oauth_password_policy') {
            column(name: "password_reminder_period", type: "bigint", defaultValue: "0", remarks: "密码修改提醒周期，单位：天")
        }
    }
	
	changeSet(author: "hzero@hand-china.com", id: "2019-01-07-oauth_password_policy"){
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        addColumn(tableName: 'oauth_password_policy') {
            column(name: "force_modify_password", type: "tinyint", defaultValue: "0", remarks: "是否强制修改初始密码")
        }
    }

    changeSet(author: "yuhuanlong@sendpular.com", id: "2022-08-06-oauth_password_policy") {
        addColumn(tableName: 'oauth_password_policy') {

            column(name: "login_again", type: "tinyint",   defaultValue:"0",   remarks: "修改密码后需要重新登入", afterColumn: 'force_modify_password')  {constraints(nullable:"false")}
            column(name: "force_code_verify", type: "tinyint",   defaultValue:"0",   remarks: "强制手机验证码校验", afterColumn: 'login_again')  {constraints(nullable:"false")}
            column(name: "enable_three_role", type: "tinyint",   defaultValue:"0",   remarks: "启用三员管理", afterColumn: 'force_code_verify')  {constraints(nullable:"false")}
            column(name: "enable_role_inherit", type: "tinyint",   defaultValue:"0",   remarks: "是否开启角色继承功能", afterColumn: 'enable_three_role')  {constraints(nullable:"false")}
            column(name: "enable_role_allocate", type: "tinyint",   defaultValue:"0",   remarks: "分配给自己的角色是否允许分配给其它用户", afterColumn: 'enable_role_inherit')  {constraints(nullable:"false")}
            column(name: "enable_role_permission", type: "tinyint",   defaultValue:"0",   remarks: "子角色是否能操作数据权限等功能", afterColumn: 'enable_role_allocate')  {constraints(nullable:"false")}
        }
    }

}