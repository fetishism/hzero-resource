package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_datasource_tl.groovy') {
    changeSet(author: "yuhuanlong@sendpulsar.com", id: "2022-08-06-hpfm_data_hierarchy_tl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        createTable(tableName: "hpfm_datasource_tl", remarks: "数据源配置多语言") {
            column(name: "datasource_id", type: "bigint",  remarks: "表ID，主键，供其他表做外键")  {constraints(nullable:"false")}
            column(name: "tenant_id", type: "bigint", defaultValue: "0", remarks: "租户ID,hpfm_tenant.tenant_id") {
                constraints(nullable: "false")
            }
            column(name: "lang", type: "varchar(" + 30 * weight + ")",  remarks: "语言")  {constraints(nullable:"false")}
            column(name: "description", type: "varchar(" + 100 * weight + ")",   defaultValue:" ",   remarks: "数据层级名称")  {constraints(nullable:"false")}
        }

        addUniqueConstraint(tableName:"hpfm_datasource_tl",columnNames:"datasource_id,lang",constraintName: "hpfm_datasource_tl_u1")
    }

}
