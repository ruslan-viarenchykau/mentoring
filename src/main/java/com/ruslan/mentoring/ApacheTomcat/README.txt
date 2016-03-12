1. Copy mod_jk.so file to %APACHE_HOME%\modules
2. Copy workers.properties to %APACHE_HOME%\conf
3. Copy modjk.conf to %APACHE_HOME%\conf
4. Open the %APACHE_HOME%\conf\httpd.conf file and append the following line:
Include conf/modjk.conf
5. Copy project\UI\src\main\webapp\age-calculator folder to the %APACHE_HOME%\htdocs (there are static content)
6. Deploy application to tomcat (run project\BUILD_WITH_MAVEN.bat)
7. Ensure that in %TOMCAT_HOME%\conf\server.xml AJP connector is enabled:
<Connector port="8009" protocol="AJP/1.3" redirectPort="8443" />
8. Go to url: %APACHE_BASE_URL%/age-calculator/age-calculator-form.html