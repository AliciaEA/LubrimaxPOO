package org.example.LubrimaxPOO.run;

import org.openxava.util.DBServer;
import org.openxava.util.AppServer;

// Añadidos para copiar directorio webapp cuando falte el target explodido
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;

/**
 * Execute this class to start the application.
 *
 * With OpenXava Studio/Eclipse: Right mouse button > Run As > Java Application
 */

public class LubrimaxPOO {

	public static void main(String[] args) throws Exception {
		DBServer.start("LubrimaxPOO-db"); // To use your own database comment this line and configure src/main/webapp/META-INF/context.xml

		// Ensure exploded webapp exists at target/LubrimaxPOO so Tomcat can start when mvn package wasn't executed
		Path exploded = Paths.get("target", "LubrimaxPOO");
		Path webapp = Paths.get("src", "main", "webapp");
		if (!Files.exists(exploded)) {
			System.out.println("Exploded webapp not found at " + exploded + ", creating from src/main/webapp...");
			if (Files.exists(webapp)) {
				try {
					Files.createDirectories(exploded);
					Files.walkFileTree(webapp, new SimpleFileVisitor<Path>() {
						@Override
						public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
							Path rel = webapp.relativize(dir);
							Path targetDir = exploded.resolve(rel.toString());
							Files.createDirectories(targetDir);
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
							Path rel = webapp.relativize(file);
							Path targetFile = exploded.resolve(rel.toString());
							Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
							return FileVisitResult.CONTINUE;
						}
					});
					System.out.println("Exploded webapp created at " + exploded);
				} catch (IOException ex) {
					System.err.println("Failed copying webapp: " + ex.getMessage());
					ex.printStackTrace();
				}
			} else {
				System.out.println("src/main/webapp not found — make sure to build the project first or create the directory.");
			}
		}

		AppServer.run("LubrimaxPOO"); // Use AppServer.run("") to run in root context
	}

}
