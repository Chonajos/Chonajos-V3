/*
 *  Copyright 2010-2011 BigData.mx
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package mx.bigdata.sat.cfdi.examples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
//import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

//import mx.bigdata.sat.cfdi.schema.Comprobante;
//import mx.bigdata.sat.cfdi.CFDv3;
import mx.bigdata.sat.security.KeyLoaderEnumeration;
import mx.bigdata.sat.security.factory.KeyLoaderFactory;
//import org.apache.xmlbeans.XmlOptions;
//import org.bouncycastle.openssl.PKCS8Generator;
//import org.bouncycastle.util.io.pem.PemObject;
//import org.bouncycastle.util.io.pem.PemWriter;
import sun.misc.BASE64Encoder;

public final class Main {

    public static void main(String[] args) throws Exception {
        CFDv32 cfd = new CFDv32(CFDv32Factory.createComprobante(), "mx.bigdata.sat.cfdi.examples");
////CHONAJOS
//        PrivateKey key = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PRIVATE_KEY_LOADER, new FileInputStream("C:/Users/Juan/Downloads/chonajos/cert/cert/CEC031212QF1.key"), "COMEX123").getKey();
//        X509Certificate cert = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PUBLIC_KEY_LOADER, new FileInputStream("C:/Users/Juan/Downloads/chonajos/cert/cert/00001000000404327545.cer")).getKey();

        FileOutputStream outFile = new FileOutputStream("C:/Users/Juan/AAA010101AAA.xml");
        PrivateKey key = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PRIVATE_KEY_LOADER, new FileInputStream("C:/Users/Juan/Downloads/AAA010101AAA/CSD01_AAA010101AAA.key"), "12345678a").getKey();
        X509Certificate cert = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PUBLIC_KEY_LOADER, new FileInputStream("C:/Users/Juan/Downloads/AAA010101AAA/CSD01_AAA010101AAA.cer")).getKey();

        ////Se genera el archivo key.pem aun no se genera correctamente
//        PrivateKey keys = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PRIVATE_KEY_LOADER, new FileInputStream("C:/Users/Juan/Downloads/AAA010101AAA/CSD01_AAA010101AAA.key"), "12345678a").getKey();
//        PemFile(keys, "RSA PUBLIC KEY");

//        PKCS8Generator encryptorBuilder = new PKCS8Generator(keys);
//        write("C:/Users/Juan/AAA010101AAAKEY1.pem");

//        //El archivo si se genera correctamente
//        X509Certificate certs = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PUBLIC_KEY_LOADER, new FileInputStream("C:/Users/Juan/Downloads/AAA010101AAA/CSD01_AAA010101AAA.cer")).getKey();
//        FileWriter fw = new FileWriter("C:/Users/Juan/AAA010101AAACER1.pem");
//        fw.write(certToString(certs));
//        fw.close();

        mx.bigdata.sat.cfdi.v32.schema.Comprobante sellado = cfd.sellarComprobante(key, cert);
        sellado.setSello(" chonajos proyect 32");
        System.err.println(sellado.getSello());
        cfd.validar();
        cfd.verificar();

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outFile);

        cfd.guardar(outFile);

        System.out.println("cadema original " + cfd.getCadenaOriginal());

    }

//    private static PemObject pemObject;
//    private static PemWriter pemWriter;

    public static void PemFile(PrivateKey key, String description) {
//        pemObject = new PemObject(description, key.getEncoded());
        String strKeyPem = new String(key.getEncoded(), Charset.defaultCharset());
        System.out.println("strKeyPem "+strKeyPem);
    }

    public static String certToString(X509Certificate cert) {
        StringWriter sw = new StringWriter();
        try {
            sw.write("-----BEGIN CERTIFICATE-----\n");
            sw.write(DatatypeConverter.printBase64Binary(cert.getEncoded()).replaceAll("(.{64})", "$1\n"));
            sw.write("\n-----END CERTIFICATE-----\n");
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }

        return sw.toString();
    }

    public static void write(String filename) throws FileNotFoundException, IOException {
//        pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(filename)));
//
//        try {
//            pemWriter.writeObject(pemObject);
//        } finally {
//            pemWriter.close();
//            System.out.println("fileName " + filename);
//        }

    }

    private static void removeSchema() {
        try {
            BufferedReader reader = null;
            System.out.println("remove");
            BufferedReader br = null;
            String lineToRemove = "xmlns:ns3=\"http://www.bigdata.mx/cfdi/example\"";
            try {
                br = new BufferedReader(new FileReader(new File("C:/Users/Juan/AAAAAAA.xml")));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            String line;
            StringBuilder sb = new StringBuilder();
            System.out.println("line " + br.readLine());
            while ((line = br.readLine()) != null) {
                System.out.println("line " + line);
                String[] strSplit = line.split("\\s+");

                for (int i = 0; i <= strSplit.length; i++) {
                    System.out.println("strSplit[i] " + strSplit[i]);
                    if (strSplit[i].equals(lineToRemove)) {
                        System.out.println("accept");
                        continue;
                    }
                }
                sb.append(line.trim());
            }

            try {
                File inputFile = new File("C:/Users/Juan/AAAAAAA.xml");
                File tempFile = new File("C:/Users/Juan/AAAAAAA.xml");
                reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
//                String lineToRemove = "xmlns:ns3=\"http://www.bigdata.mx/cfdi/example\"";
                String currentLine;

                while ((currentLine = reader.readLine()) != null) {
                    System.out.println("in while");
                    String trimmedLine = currentLine.trim();
                    System.out.println("trimmedLine " + trimmedLine);
                    if (trimmedLine.equals(lineToRemove)) {
                        continue;
                    }
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();
                boolean successful = tempFile.renameTo(inputFile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
