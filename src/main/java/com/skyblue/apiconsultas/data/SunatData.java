package com.skyblue.apiconsultas.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sunat_data_ruc")
public class SunatData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ruc;
    private String nombreRazonSocial;
    private String estadoContribuyente;
    private String condicionDomicilio;
    private String ubigeo;
    private String tipoVia;
    private String nombreVia;
    private String codigoZona;
    private String tipoZona;
    private String numero;
    private String interior;
    private String lote;
    private String departamento;
    private String manzana;
    private String kilometro;

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
