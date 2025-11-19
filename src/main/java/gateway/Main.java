package gateway;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import gateway.filter.AuthenticationFilter;
import gateway.filter.CorsFilter;
import gateway.filter.RateLimitFilter;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(8081); // Puerto diferente al backend
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder holder = context.addServlet(ServletContainer.class, "/api/*");
        holder.setInitOrder(1);

        // Escanear paquetes del gateway
        holder.setInitParameter("jersey.config.server.provider.packages", "gateway.controller");
        
        // Registrar filtros de seguridad
        holder.setInitParameter("jersey.config.server.provider.classnames", 
            "org.glassfish.jersey.jackson.JacksonFeature," +
            "gateway.filter.CorsFilter," +
            "gateway.filter.RateLimitFilter," +
            "gateway.filter.AuthenticationFilter");

        try {
            server.start();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║   🛡️  API Gateway iniciado en http://localhost:8081  ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("🔒 Capas de seguridad activas:");
            System.out.println("   ✓ CORS Protection");
            System.out.println("   ✓ Rate Limiting");
            System.out.println("   ✓ JWT Authentication");
            System.out.println("   ✓ Request Validation");
            System.out.println();
            System.out.println("📡 Ruteando peticiones a backend: http://java-jpa-app:8080");
            
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error al iniciar el API Gateway: " + e.getMessage());
        } finally {
            server.destroy();
        }
    }
}