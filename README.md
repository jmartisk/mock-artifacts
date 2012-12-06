mock-artifacts
==============

Java EE mock artifacts for testing and simple demonstration

Source codes released under DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE.
License text available at: http://sam.zoy.org/wtfpl/COPYING

Available applications:

* dummy-servlet30
WAR application containing a simple Hello World servlet. Servlet 3.0 [Java EE6, Tomcat 7] compliant.
The servlet has '/' as its url pattern.

* dummy-servlet25
WAR application containing a simple Hello World servlet. Servlet 2.5 [Java EE5, Tomcat 6] compliant.
The servlet has '/' as its url pattern.

* dummyejb31-javaee6
WAR application containing a Stateful bean, Stateless bean and a Singleton bean. All of them are called
using CDI if you access the servlet under '/' url pattern.