/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.standalone;

import java.net.URL;
import java.util.Objects;
import javax.websocket.server.ServerContainer;
import org.dizitart.nitrite.datagate.factory.DatagateAuthenticatorFactory;
import org.dizitart.nitrite.datagate.session.DataGateSession;
import org.dizitart.nitrite.datagate.standalone.authentication.DatagateJongoAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

public class Main {

  public static void main(String args[]) throws Exception {
    setupDatagate();
    startServer();
  }

  private static void setupDatagate() {
    DatagateAuthenticatorFactory.getInstance().setAuthenticator(DatagateJongoAuthenticator.class);

  }

  private static void startServer() throws Exception {

    Server server = new Server(8080);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    // Add javax.websocket support
    ServerContainer container = WebSocketServerContainerInitializer.configureContext(context);

    // Add echo endpoint to server container
    container.addEndpoint(DataGateSession.class);

    // Add default servlet (to serve the html/css/js)
    // Figure out where the static files are stored.
    URL urlStatics = Thread.currentThread().getContextClassLoader().getResource("index.html");
    Objects.requireNonNull(urlStatics, "Unable to find index.html in classpath");
    String urlBase = urlStatics.toExternalForm().replaceFirst("/[^/]*$", "/");
    ServletHolder defHolder = new ServletHolder("default", new DefaultServlet());
    defHolder.setInitParameter("resourceBase", urlBase);
    defHolder.setInitParameter("dirAllowed", "true");
    context.addServlet(defHolder, "/");
    try {
      server.start();
      server.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
