/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.managedbeans;

import com.google.inject.Inject;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.entities.TipoIdentificacion;
import edu.eci.pdsw.samples.services.ExcepcionServiciosSuscripciones;
import edu.eci.pdsw.samples.services.ServiciosPaciente;
import edu.eci.pdsw.samples.services.ServiciosPacientesFactory;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author hcadavid
 */
@ManagedBean(name = "mb")
@SessionScoped
public class PacientesBean {

    @Inject
    ServiciosPaciente serviciosPaciente;
    TipoIdentificacion tipoIdentificacion = TipoIdentificacion.CC;

    int idPaciente = getPaciente().getId();


    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public Paciente getPaciente(){
        try {
            return serviciosPaciente.consultarPacientesPorId(idPaciente,tipoIdentificacion);
        } catch (ExcepcionServiciosSuscripciones e) {
            throw new RuntimeException(e);
        }
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public List<Paciente> getData() throws Exception{
        try {
            return ServiciosPacientesFactory.getInstance().getForumsServices().consultarPacientes();
        } catch (ExcepcionServiciosSuscripciones ex) {
            
            throw ex;
        }
        
    }

    public TipoIdentificacion[] getTiposIdentificacion() {
        return TipoIdentificacion.values();
    }
    
}
