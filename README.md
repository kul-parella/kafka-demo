# kafka-demo
kafka-demo project demonstrates spring boot kafka project both producer and consumer retry mechanism


commands for reference:

start Zookeeper:

zookeeper-server-start.bat C:\kafka_2.12-2.4.1\config\zookeeper.properties

start broker:

kafka-server-start.bat C:\kafka_2.12-2.4.1\config\server.properties


kafka- topic creation syntax with out in-sync replication factor:

kafka-topics --zookeeper 127.0.0.1:2181 --topic 'topic-Name' --create --partitions 3 --replication-factor 1

topics needed to run this codebase:

kafka-topics --zookeeper 127.0.0.1:2181 --topic kafka-sample --create --partitions 3 --replication-factor 1

kafka-topics --zookeeper 127.0.0.1:2181 --topic kafka-sample_delay1 --create --partitions 3 --replication-factor 1

kafka-topics --zookeeper 127.0.0.1:2181 --topic kafka-sample_delay2 --create --partitions 3 --replication-factor 1

kafka-topics --zookeeper 127.0.0.1:2181 --topic kafka-sample_final --create --partitions 3 --replication-factor 1

kafka-topics --zookeeper 127.0.0.1:2181 --topic kafka-sample_nonRecoverable --create --partitions 3 --replication-factor 1


with min.insync.replication-factor for producer retry:

kafka-topics --zookeeper 127.0.0.1:2181 --topic 'topic-Name' --create --partitions 3 --replication-factor 3 --config min.insync.replicas=2

