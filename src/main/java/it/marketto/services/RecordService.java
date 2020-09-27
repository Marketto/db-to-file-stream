package it.marketto.services;

import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.marketto.components.RecordProcessor;

@Service
public class RecordService {
	
	@Autowired
	private RecordProcessor recordProcessor;

	@Transactional(readOnly = true)
	public void transformToOutputStream(OutputStream outputStream) {
		recordProcessor.processRecord(outputStream);
	}
}
