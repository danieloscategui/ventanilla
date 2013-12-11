package com.pe.pgn.clubpgn.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.pgn.clubpgn.common.CLPConstantes;
import com.pe.pgn.clubpgn.dao.EmpresaAfiliadoraDao;
import com.pe.pgn.clubpgn.domain.ClpbEmpresaAfiliadora;
import com.pe.pgn.clubpgn.domain.beans.BNEmpresaAfiliadora;
import com.pe.pgn.clubpgn.domain.beans.BNProgramaDetalle;
import com.pe.pgn.clubpgn.service.EmpresaAfiliadoraService;

@Service
public class EmpresaAfiliadoraServiceImpl 
		extends GenericManagerImpl<ClpbEmpresaAfiliadora, Long>
		implements EmpresaAfiliadoraService {

	@Autowired
	private EmpresaAfiliadoraDao empresaAfiliadoraDao;
	
	@Override
	public List<BNEmpresaAfiliadora> listarTodasLasEmpresasAfiliadoras() {
		return empresaAfiliadoraDao.listarTodasLasEmpresasAfiliadoras();
	}

	@Override
	public List<BNProgramaDetalle> obtenerProgramasActivosParaEmpresasAfiliadoras() {
		return empresaAfiliadoraDao.obtenerProgramasActivosParaEmpresasAfiliadoras();
	}

	@Override
	public BNEmpresaAfiliadora obtenerEmpresaAfiliadoraPorId(BigDecimal coEmpresaAfiliadora) {
		
		BNEmpresaAfiliadora afiliadora = empresaAfiliadoraDao.obtenerEmpresaAfiliadoraPorId(coEmpresaAfiliadora);
		if(afiliadora.getStStrEmpresaAfiliadora().equalsIgnoreCase(CLPConstantes.ST_VERDADERO)){
			afiliadora.setStEmpresaAfiliadora(Boolean.TRUE);
		}
		return afiliadora;
	}

	@Override
	public void guardarEmpresaAfiliadora(BNEmpresaAfiliadora afiliadora) {
		empresaAfiliadoraDao.guardarEmpresaAfiliadora(afiliadora);
	}

	@Override
	public boolean esEmpresaAfiliadoraConDependencias(
			BigDecimal coEmpresaAfiliadora) {
		return empresaAfiliadoraDao.esEmpresaAfiliadoraConDependencias(coEmpresaAfiliadora);
	}

	@Override
	public void eliminarEmpresaAfiliadora(BigDecimal coEmpresaAfiliadora) {
		empresaAfiliadoraDao.eliminarEmpresaAfiliadora(coEmpresaAfiliadora);
	}

}