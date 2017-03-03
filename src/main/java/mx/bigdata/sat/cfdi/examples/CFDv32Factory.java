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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.annotation.*;

import mx.bigdata.sat.cfdi.v32.schema.ObjectFactory;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante;

public final class CFDv32Factory {

    public static Comprobante createComprobante() throws Exception {

        ObjectFactory of = new ObjectFactory();
        Comprobante comp = of.createComprobante();
        comp.setVersion("3.2");
        comp.setSerie("A");
        comp.setFolio("11639");
        Date date = new GregorianCalendar(2017, 01, 28, 9, 5, 12).getTime();
        comp.setFecha(date);
        comp.setFormaDePago("PAGO EN UNA SOLA EXHIBICION");
        comp.setSubTotal(new BigDecimal("3355.000000"));
        comp.setMetodoDePago("01");
        comp.setDescuento(new BigDecimal("0.000000"));
        comp.setTotal(new BigDecimal("3355.000000"));
        
        comp.setTipoDeComprobante("ingreso");
        comp.setLugarExpedicion("09040");
        comp.setMoneda("MXN");
        comp.setNoCertificado("00001000000404327545");//es el numero del certificado
        comp.setEmisor(createEmisor(of));
        comp.setReceptor(createReceptor(of));
        comp.setConceptos(createConceptos(of));
        comp.setImpuestos(createImpuestos(of));

//    comp.setAddenda(createAddenda(of));
        return comp;
    }

    private static Comprobante.Emisor createEmisor(ObjectFactory of) {
        Comprobante.Emisor emisor = of.createComprobanteEmisor();
        emisor.setNombre("COMERCIALIZADORA Y EXPORTADORA CHONAJOS S DE RL DE CV");
        emisor.setRfc("AAA010101AAA");
//        emisor.setRfc("CEC031212QF1");

        mx.bigdata.sat.cfdi.v32.schema.TUbicacionFiscal uf = of.createTUbicacionFiscal();
        uf.setCalle("AVENIDA TRABAJADORES SOCIALES EJE 5 ZONA V SECCION 5 NAVE 2");
        uf.setNoExterior("BODEGA Q 85");
        
        uf.setColonia("CENTRAL DE ABASTO");
        uf.setLocalidad("MEXICO");
        uf.setMunicipio("IZTAPALAPA");
        uf.setEstado("DISTRITO FEDERAL");
        uf.setPais("MEXICO");
        uf.setCodigoPostal("09040");
        emisor.setDomicilioFiscal(uf);

        //EXPEDIDO EN NO ES OBLIGATORIO
//    mx.bigdata.sat.cfdi.v32.schema.TUbicacion u = of.createTUbicacion();
//    u.setCalle("AV. UNIVERSIDAD");
//    u.setCodigoPostal("09040");
//    u.setColonia("CENTRAL DE ABASTOS"); 
//    u.setEstado("DISTRITO FEDERAL");
//    u.setNoExterior("Q85");
//    u.setPais("Mexico"); 
//    emisor.setExpedidoEn(u);
//REGIMEN FISCAL
        Comprobante.Emisor.RegimenFiscal regimenFiscal = new Comprobante.Emisor.RegimenFiscal();
        regimenFiscal.setRegimen("REGIMEN GENERAL DE LEY");
        ArrayList<Comprobante.Emisor.RegimenFiscal> lstRegimenFiscal = new ArrayList<Comprobante.Emisor.RegimenFiscal>();
        lstRegimenFiscal.add(regimenFiscal);

//        emisor.setRegimenFiscal(lstRegimenFiscal);
        emisor.getRegimenFiscal().add(regimenFiscal);

        return emisor;
    }

    private static Comprobante.Receptor createReceptor(ObjectFactory of) {

        Comprobante.Receptor receptor = of.createComprobanteReceptor();
        receptor.setNombre("CAROLINA VEGA HERNANDEZ");
        receptor.setRfc("VEHC761003U32");

        mx.bigdata.sat.cfdi.v32.schema.TUbicacion uf = of.createTUbicacion();
        uf.setCalle("AVENIDA 20 DE NOVIEMBRE ENTRE CALLE SIMON BOLIVAR Y CALLE AMADO");
        uf.setColonia("SAN JUAN");
        uf.setMunicipio("TAMAZUNCHALE");
        uf.setEstado("SAN LUIS POTOSI");
        uf.setPais("MEXICO");
        uf.setCodigoPostal("79960");

        //NO SON OBLIGATORIOS NO SE OCUPAN POR AHORA
//    uf.setNoExterior("16 EDF 3"); 
//    uf.setNoInterior("DPTO 101"); 
        receptor.setDomicilio(uf);
        return receptor;
    }

    private static Comprobante.Conceptos createConceptos(ObjectFactory of) {
        Comprobante.Conceptos cps = of.createComprobanteConceptos();
        List<Comprobante.Conceptos.Concepto> list = cps.getConcepto();

        Comprobante.Conceptos.Concepto c1 = of.createComprobanteConceptosConcepto();
        c1.setCantidad(new BigDecimal("55"));
        c1.setUnidad("KILOS");
        c1.setDescripcion("AJO CHILENO");
        c1.setValorUnitario(new BigDecimal("61.00"));
        c1.setImporte(new BigDecimal("3355.000000"));

        list.add(c1);
        return cps;
    }

    private static Comprobante.Impuestos createImpuestos(ObjectFactory of) {
        Comprobante.Impuestos imps = of.createComprobanteImpuestos();
//        Comprobante.Impuestos.Traslados trs = of.createComprobanteImpuestosTraslados();
//        List<Comprobante.Impuestos.Traslados.Traslado> list = trs.getTraslado();
//        Comprobante.Impuestos.Traslados.Traslado t1 = of.createComprobanteImpuestosTrasladosTraslado();
//        t1.setImporte(new BigDecimal("0.00"));
//        t1.setImpuesto("IVA");
//        t1.setTasa(new BigDecimal("0.00"));
//        list.add(t1);
//        Comprobante.Impuestos.Traslados.Traslado t2 = of.createComprobanteImpuestosTrasladosTraslado();
//        t2.setImporte(new BigDecimal("22.07"));
//        t2.setImpuesto("IVA");
//        t2.setTasa(new BigDecimal("16.00"));
//        list.add(t2);
//        imps.setTraslados(trs);
        return imps;
    }

    private static Comprobante.Addenda createAddenda(ObjectFactory of) {
        Comprobante.Addenda addenda = of.createComprobanteAddenda();
        Company c = new Company();
        c.transaction = new Transaction();
        c.transaction.purchaseOrder = "4600364283";
        addenda.getAny().add(c);
        return addenda;
    }

    @XmlRootElement(name = "Company")
    private final static class Company {

        @XmlElement(name = "Transaction")
        Transaction transaction;
    }

    @XmlRootElement
    private final static class Transaction {

        @SuppressWarnings("unused")
        @XmlAttribute(name = "PurchaseOrder")
        String purchaseOrder;
    }
}
