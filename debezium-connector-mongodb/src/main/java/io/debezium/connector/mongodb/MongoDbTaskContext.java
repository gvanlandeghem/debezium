/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.mongodb;

import java.util.Collections;

import io.debezium.config.Configuration;
import io.debezium.connector.common.CdcSourceTaskContext;
import io.debezium.heartbeat.Heartbeat;
import io.debezium.schema.TopicSelector;

/**
 * @author Randall Hauch
 */
public class MongoDbTaskContext extends CdcSourceTaskContext {

    private final Filters filters;
    private final SourceInfo source;
    private final TopicSelector<CollectionId> topicSelector;
    private final String serverName;
    private final ConnectionContext connectionContext;
    private final MongoDbConnectorConfig connectorConfig;

    /**
     * @param config the configuration
     */
    public MongoDbTaskContext(Configuration config) {
        super("MongoDB", config.getString(MongoDbConnectorConfig.LOGICAL_NAME), Collections::emptySet);

        final String serverName = config.getString(MongoDbConnectorConfig.LOGICAL_NAME);
        this.filters = new Filters(config);
        this.source = new SourceInfo(new MongoDbConnectorConfig(config));
        this.topicSelector = MongoDbTopicSelector.defaultSelector(serverName, config.getString(Heartbeat.HEARTBEAT_TOPICS_PREFIX));
        this.serverName = config.getString(MongoDbConnectorConfig.LOGICAL_NAME);
        this.connectionContext = new ConnectionContext(config);
        this.connectorConfig = new MongoDbConnectorConfig(config);
    }

    public TopicSelector<CollectionId> topicSelector() {
        return topicSelector;
    }

    public Filters filters() {
        return filters;
    }

    public SourceInfo source() {
        return source;
    }

    public String serverName() {
        return serverName;
    }

    public ConnectionContext getConnectionContext() {
        return connectionContext;
    }

    public MongoDbConnectorConfig getConnectorConfig() {
        return this.connectorConfig;
    }
}
