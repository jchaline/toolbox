<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

    <parent>
        <artifactId>lutece-parent-pom</artifactId>
        <groupId>fr.paris.lutece</groupId>
        <version>2.31</version>
    </parent>
  <modelVersion>4.0.0</modelVersion>
  <groupId>fr.paris.lutece.plugins</groupId>
  <artifactId>plugin-${(plugin.name)!'pluginName'}</artifactId>
  <packaging>lutece-plugin</packaging>
  <version>1.0.0-SNAPSHOT</version>
  <name>Lutece ${(plugin.name?cap_first)!'pluginName'} plugin</name>

	<repositories>
	  <repository>
	    <id>lutece</id>
	    <name>luteceRepository</name>
	    <url>http://dev.lutece.paris.fr/maven_repository</url>
	    <layout>default</layout>
	  </repository>
	  <repository>
      <id>repository.jboss.org-public</id>
      <name>JBoss.org Maven repository</name>
      <url>https://repository.jboss.org/nexus/content/groups/public</url>
    </repository>	
	</repositories>

  <dependencies>
      
    <dependency>
      <groupId>fr.paris.lutece</groupId>
      <artifactId>lutece-core</artifactId>
      <version>4.1.1</version>
      <type>lutece-core</type>
    </dependency>
	</dependencies>
	
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>2.3.2</version>
				<configuration>
					<source>1.6</source>
					<target>1.6</target>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>