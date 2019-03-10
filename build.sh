#!/usr/bin/env bash

mvn clean install package -DskipTests

docker cp target/releases/elasticsearch-turkish-analysis-1.0-SNAPSHOT.zip elasticsearch:/elasticsearch-turkish-analysis-1.0-SNAPSHOT.zip

docker exec -it elasticsearch bash -c "yes | ./bin/elasticsearch-plugin install file:///elasticsearch-turkish-analysis-1.0-SNAPSHOT.zip"

docker cp config/tokenization/abbreviations.txt elasticsearch:/usr/share/elasticsearch/plugins/elasticsearch-turkish-analysis/abbreviations.txt

docker exec -it elasticsearch bash -c "export ABBREVIATIONS_FILE_PATH=/usr/share/elasticsearch/plugins/elasticsearch-turkish-analysis/abbreviations.txt"

docker restart elasticsearch