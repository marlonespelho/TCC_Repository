package br.com.integrado.api.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.AgendaModel;
import br.com.integrado.api.entities.AgendamentoModel;
import br.com.integrado.api.enums.StatusAgendamentoEnum;
import br.com.integrado.api.repositories.AgendamentoRepository;

@Service
public class AgendamentosService {
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	public void criarAgendamentos(AgendaModel agenda) {
		int quantHorarios = (int) (agenda.getHoraFinal().getTime()/3600000 - agenda.getHoraInicial().getTime()/3600000) * 2;
		GregorianCalendar inicioHorario = new GregorianCalendar();
		inicioHorario.setTime(agenda.getHoraInicial());
		GregorianCalendar fimHorario = new GregorianCalendar();
		fimHorario.setTime(agenda.getHoraInicial());
		fimHorario.add(Calendar.MINUTE, 30);
		for (int i = 1; i <= quantHorarios; i++) {
			AgendamentoModel agendamento = new AgendamentoModel(agenda, inicioHorario.getTime(), 
					fimHorario.getTime(), StatusAgendamentoEnum.ABERTO);
			this.agendamentoRepository.save(agendamento);
			inicioHorario.add(Calendar.MINUTE, 30);
			fimHorario.add(Calendar.MINUTE, 30);
		}
	}
	
}
