package com.pe.pgn.clubpgn.service;

import java.math.BigDecimal;
import java.util.List;

import com.pe.pgn.clubpgn.domain.ClpbEmpresaAfiliadora;
import com.pe.pgn.clubpgn.domain.beans.BNEmpresaAfiliadora;
import com.pe.pgn.clubpgn.domain.beans.BNProgramaDetalle;

public interface EmpresaAfiliadoraService extends GenericManager<ClpbEmpresaAfiliadora, Long> {

	public List<BNEmpresaAfiliadora> listarTodasLasEmpresasAfiliadoras();
	public List<BNProgramaDetalle> obtenerProgramasActivosParaEmpresasAfiliadoras();
	public BNEmpresaAfiliadora obtenerEmpresaAfiliadoraPorId(BigDecimal coEmpresaAfiliadora);
	public void guardarEmpresaAfiliadora(BNEmpresaAfiliadora afiliadora);
	public boolean esEmpresaAfiliadoraConDependencias(BigDecimal coEmpresaAfiliadora);
	public void eliminarEmpresaAfiliadora(BigDecimal coEmpresaAfiliadora);
}
