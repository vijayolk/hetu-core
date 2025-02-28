/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.plugin.kafka;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import io.airlift.configuration.Config;
import io.airlift.units.DataSize;
import io.airlift.units.DataSize.Unit;
import io.airlift.units.Duration;
import io.airlift.units.MinDuration;
import io.prestosql.spi.HostAddress;
import io.prestosql.spi.function.Mandatory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.File;
import java.util.Set;

import static com.google.common.collect.Iterables.transform;

public class KafkaConnectorConfig
{
    private static final int KAFKA_DEFAULT_PORT = 9092;

    /**
     * Seed nodes for Kafka cluster. At least one must exist.
     */
    private Set<HostAddress> nodes = ImmutableSet.of();

    /**
     * Timeout to connect to Kafka.
     */
    private Duration kafkaConnectTimeout = Duration.valueOf("10s");

    /**
     * Buffer size for connecting to Kafka.
     */
    private DataSize kafkaBufferSize = new DataSize(64, Unit.KILOBYTE);

    /**
     * The schema name to use in the connector.
     */
    private String defaultSchema = "default";

    /**
     * Set of tables known to this connector. For each table, a description file may be present in the catalog folder which describes columns for the given topic.
     */
    private Set<String> tableNames = ImmutableSet.of();

    /**
     * Folder holding the JSON description files for Kafka topics.
     */
    private File tableDescriptionDir = new File("etc/kafka/");

    /**
     * Whether internal columns are shown in table metadata or not. Default is no.
     */
    private boolean hideInternalColumns = true;

    /**
     * the path of krb5.conf ,used for develop
     */
    private String krb5Conf;

    /**
     * the path of kafka_client_jaas_conf
     */
    private String loginConfig;

    /**
     * whether use subject creds only
     */
    private String useSubjectCredsOnly;

    /**
     * the group id of kafka
     */
    private String groupId;

    /**
     * the security protocol of kafka
     */
    private String securityProtocol;

    /**
     * the sasl mechanism of kafka
     */
    private String saslMechanism;

    /**
     * the sasl kerberos service name of kafka
     */
    private String saslKerberosServiceName;

    /**
     * whether to use kerberos
     */
    private String kerberosOn;
    /**
     * whether to use user and password
     */
    private String userPasswordOn;

    public String getUserPasswordOn()
    {
        return userPasswordOn;
    }

    @Mandatory(name = "user.password.auth.on",
            description = "user.password.auth.on",
            defaultValue = "",
            required = false)
    @Config("user.password.auth.on")
    public KafkaConnectorConfig setUserPasswordOn(String userPasswordOn)
    {
        this.userPasswordOn = userPasswordOn;
        return this;
    }

    public String getKrb5Conf()
    {
        return krb5Conf;
    }

    @Mandatory(name = "java.security.krb5.conf",
            description = "java.security.krb5.conf",
            defaultValue = "",
            required = false)
    @Config("java.security.krb5.conf")
    public KafkaConnectorConfig setKrb5Conf(String krb5Conf)
    {
        this.krb5Conf = krb5Conf;
        return this;
    }

    public String getLoginConfig()
    {
        return loginConfig;
    }

    @Mandatory(name = "sasl.jaas.config",
            description = "sasl.jaas.config",
            defaultValue = "",
            required = false)
    @Config("sasl.jaas.config")
    public KafkaConnectorConfig setLoginConfig(String loginConfig)
    {
        this.loginConfig = loginConfig;
        return this;
    }

    public String getGroupId()
    {
        return groupId;
    }

    @Mandatory(name = "group.id",
            description = "group.id",
            defaultValue = "test",
            required = false)
    @Config("group.id")
    public KafkaConnectorConfig setGroupId(String groupId)
    {
        this.groupId = groupId;
        return this;
    }

    public String getSecurityProtocol()
    {
        return securityProtocol;
    }

    @Mandatory(name = "security.protocol",
            description = "security.protocol",
            defaultValue = "SASL_PLAINTEXT",
            required = false)
    @Config("security.protocol")
    public KafkaConnectorConfig setSecurityProtocol(String securityProtocol)
    {
        this.securityProtocol = securityProtocol;
        return this;
    }

    public String getSaslMechanism()
    {
        return saslMechanism;
    }

    @Mandatory(name = "sasl.mechanism",
            description = "sasl.mechanism",
            defaultValue = "GSSAPI",
            required = false)
    @Config("sasl.mechanism")
    public KafkaConnectorConfig setSaslMechanism(String saslMechanism)
    {
        this.saslMechanism = saslMechanism;
        return this;
    }

    public String getSaslKerberosServiceName()
    {
        return saslKerberosServiceName;
    }

    @Mandatory(name = "sasl.kerberos.service.name",
            description = "sasl.kerberos.service.name",
            defaultValue = "kafka",
            required = false)
    @Config("sasl.kerberos.service.name")
    public KafkaConnectorConfig setSaslKerberosServiceName(String saslKerberosServiceName)
    {
        this.saslKerberosServiceName = saslKerberosServiceName;
        return this;
    }

    public String isKerberosOn()
    {
        return kerberosOn;
    }

    @Mandatory(name = "kerberos.on",
            description = "whether to use kerberos",
            defaultValue = "false",
            required = false)
    @Config("kerberos.on")
    public KafkaConnectorConfig setKerberosOn(String kerberosOn)
    {
        this.kerberosOn = kerberosOn;
        return this;
    }

    @NotNull
    public File getTableDescriptionDir()
    {
        return tableDescriptionDir;
    }

    @Config("kafka.table-description-dir")
    public KafkaConnectorConfig setTableDescriptionDir(File tableDescriptionDir)
    {
        this.tableDescriptionDir = tableDescriptionDir;
        return this;
    }

    @NotNull
    public Set<String> getTableNames()
    {
        return tableNames;
    }

    @Mandatory(name = "kafka.table-names",
            description = "List of all tables provided by the catalog",
            defaultValue = "table1,table2",
            required = true)
    @Config("kafka.table-names")
    public KafkaConnectorConfig setTableNames(String tableNames)
    {
        this.tableNames = ImmutableSet.copyOf(Splitter.on(',').omitEmptyStrings().trimResults().split(tableNames));
        return this;
    }

    @NotNull
    public String getDefaultSchema()
    {
        return defaultSchema;
    }

    @Mandatory(name = "kafka.default-schema",
            description = "Default schema name to use",
            defaultValue = "default")
    @Config("kafka.default-schema")
    public KafkaConnectorConfig setDefaultSchema(String defaultSchema)
    {
        this.defaultSchema = defaultSchema;
        return this;
    }

    @Size(min = 1)
    public Set<HostAddress> getNodes()
    {
        return nodes;
    }

    @Mandatory(name = "kafka.nodes",
            description = "List of nodes in the Kafka cluster ",
            defaultValue = "host1:port,host2:port",
            required = true)
    @Config("kafka.nodes")
    public KafkaConnectorConfig setNodes(String nodes)
    {
        this.nodes = (nodes == null) ? null : parseNodes(nodes);
        return this;
    }

    @MinDuration("1s")
    public Duration getKafkaConnectTimeout()
    {
        return kafkaConnectTimeout;
    }

    @Config("kafka.connect-timeout")
    public KafkaConnectorConfig setKafkaConnectTimeout(String kafkaConnectTimeout)
    {
        this.kafkaConnectTimeout = Duration.valueOf(kafkaConnectTimeout);
        return this;
    }

    public DataSize getKafkaBufferSize()
    {
        return kafkaBufferSize;
    }

    @Config("kafka.buffer-size")
    public KafkaConnectorConfig setKafkaBufferSize(String kafkaBufferSize)
    {
        this.kafkaBufferSize = DataSize.valueOf(kafkaBufferSize);
        return this;
    }

    public boolean isHideInternalColumns()
    {
        return hideInternalColumns;
    }

    @Config("kafka.hide-internal-columns")
    public KafkaConnectorConfig setHideInternalColumns(boolean hideInternalColumns)
    {
        this.hideInternalColumns = hideInternalColumns;
        return this;
    }

    public static ImmutableSet<HostAddress> parseNodes(String nodes)
    {
        Splitter splitter = Splitter.on(',').omitEmptyStrings().trimResults();
        return ImmutableSet.copyOf(transform(splitter.split(nodes), KafkaConnectorConfig::toHostAddress));
    }

    private static HostAddress toHostAddress(String value)
    {
        return HostAddress.fromString(value).withDefaultPort(KAFKA_DEFAULT_PORT);
    }
}
