package com.web.chon.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;


import org.apache.commons.io.IOUtils;

public class FileUtils {

	/**
	 * Obtiene el contenido de un <code>InputStream</code> como un
	 * <code>byte[]</code>.
	 * <p>
	 * Este metodo usa un buffer internamente, por lo tanto no es necesario usar
	 * un <code>BufferedInputStream</code>.
	 * 
	 * <p>
	 * Este metodo <em>NO</em> cierra el <code>InputStream</code> despues de la
	 * operacion de lectura; es responsabilidad del que llama cerrar el stream
	 * si lo desea.
	 * 
	 * @param input
	 *            el <code>InputStream</code> a leer
	 * @return el arregl de bytes solicitado
	 * @throws NullPointerException
	 *             si la entrada es nula
	 * @throws IOException
	 *             si ocuerre un error de tipo I/O
	 */
	public static byte[] inputStreamTobyte(InputStream is) throws IOException {

		return IOUtils.toByteArray(is);

	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();

		if (length > Integer.MAX_VALUE) {
			// Archivo muy grande que hacer?
		}

		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			is.close();
			throw new IOException("No se pudo leer completamente el archivo "
					+ file.getName());
		}

		// Cierra stream regresa los bytes
		is.close();
		return bytes;
	}

	/**
	 * Compia el archivo origen en el destino.
	 * 
	 * @param source
	 *            Es el archivo origen
	 * @param output
	 *            Es el archivo destino.
	 * @throws Exception 
	 */
	public static void copyFile(File source, File output) throws Exception {
		FileChannel in = null;
		FileChannel out = null;
		try {
			in = (new FileInputStream(source)).getChannel();
			out = (new FileOutputStream(output)).getChannel();
			in.transferTo(0, source.length(), out);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (in != null) in.close();
			} catch (IOException e) {}
			try {
				if (out != null) out.close();
			} catch (IOException e) {}
		}

	}
	
	/**
	 * Metodo que crea una nueva carpeta en base a la ruta enviada.
	 * @param ruta Nombre del path
	 * @throws IOException
	 */
	public static void creaCarpeta(String ruta) throws IOException {
        File dir = new File(ruta);
            System.out.println("ruta "+ruta);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Imposible crear carpeta: " + ruta);
            }
        }
    }
	
	/**
	 * M&eactue;todo que guarda un Inputstream en FileSystem,
	 * de acuerdo a la ruta y nombre enviados.
	 * 
	 * @param fileName nombre del archivo con la ruta completa
	 * @param datos
	 * @throws IOException
	 */
	public static void guardaArchivo(String fileName, byte[] datos) throws IOException {
        
        File archivo = new File(fileName);
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(archivo));
 
        outputStream.write(datos);
        outputStream.close();
    }
	
	/**
	 * Copia un archivo en una ruta especifica.
	 * 
	 * @param fileName nombre del archivo con la ruta completa
	 * @param in stream del arhivo
	 * @return
	 * @throws IOException 
	 */
	public static void guardaArchivo(String fileName, InputStream in) throws IOException {
		
			OutputStream out = new FileOutputStream(fileName);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
		
	}

}
