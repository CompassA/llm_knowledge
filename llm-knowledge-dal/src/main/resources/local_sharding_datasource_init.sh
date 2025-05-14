# 创建16个分库 各128张表
for i in {0..15}; do
    data_base_name="knowledge_flow_sharding_$i"
    mysql -uroot -prootroot -e "CREATE DATABASE IF NOT EXISTS $data_base_name; "
    for j in {0..127}; do
        table_name="test_sharding_$j"
        mysql -uroot -prootroot -e "
            USE $data_base_name;
            CREATE TABLE IF NOT EXISTS $table_name (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                sharding_id VARCHAR(64) NOT NULL,
                col_1 VARCHAR(128) NOT NULL,
                col_2 VARCHAR(128) NOT NULL,
                col_3 VARCHAR(128) NOT NULL,
                create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                CONSTRAINT unq_sharding_id UNIQUE (sharding_id)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;"
    done
done