package com.skyblue.apiconsultas.dto;

import com.skyblue.apiconsultas.data.SunatData;

public record SunatRuc(

         String ruc,
         String nombreRazonSocial,
         String estadoContribuyente,
         String condicionDomicilio,
         String ubigeo,
         String tipoVia,
         String nombreVia,
         String codigoZona,
         String tipoZona,
         String numero,
         String interior,
         String lote,
         String departamento,
         String manzana,
         String kilometro
) {

    public SunatRuc(String[] data) {
        this(data[0],
                data[1],
                data[2],
                data[3],
                data[4],
                data[5],
                data[6],
                data[7],
                data[8],
                data[9],
                data[10],
                data[11],
                data[12],
                data[13],
                data[14]);
    }
    public SunatData toEntity(){
        return new SunatData(
                null,
                ruc,
                nombreRazonSocial,
                estadoContribuyente,
                condicionDomicilio,
                ubigeo,
                tipoVia,
                nombreVia,
                codigoZona,
                tipoZona,
                numero,
                interior,
                lote,
                departamento,
                manzana,
                kilometro
        );}


    public boolean hasChanges(SunatData sunatData) {
        return !ruc.equals(sunatData.getRuc()) ||
                !nombreRazonSocial.equals(sunatData.getNombreRazonSocial()) ||
                !estadoContribuyente.equals(sunatData.getEstadoContribuyente()) ||
                !condicionDomicilio.equals(sunatData.getCondicionDomicilio()) ||
                !ubigeo.equals(sunatData.getUbigeo()) ||
                !tipoVia.equals(sunatData.getTipoVia()) ||
                !nombreVia.equals(sunatData.getNombreVia()) ||
                !codigoZona.equals(sunatData.getCodigoZona()) ||
                !tipoZona.equals(sunatData.getTipoZona()) ||
                !numero.equals(sunatData.getNumero()) ||
                !interior.equals(sunatData.getInterior()) ||
                !lote.equals(sunatData.getLote()) ||
                !departamento.equals(sunatData.getDepartamento()) ||
                !manzana.equals(sunatData.getManzana()) ||
                !kilometro.equals(sunatData.getKilometro());
    }
}
