package it.marketto.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import it.marketto.services.RecordService;

@RestController
public class Controller {
	
	@Autowired
	private RecordService recordService;

	@RequestMapping(value = "/streaming-response-body")
	public StreamingResponseBody  pippo(HttpServletResponse response) {
		response.setContentType("text/plain; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=sample.csv");
		return new StreamingResponseBody() {
            @Override
        	@Transactional(readOnly = true)
            public void writeTo (OutputStream outputStream) throws UnsupportedEncodingException {
            	recordService.transformToOutputStream(outputStream);
            }
        };
	}
	
	@Transactional(readOnly = true)
	@RequestMapping("/servlet")
	public void pluto(HttpServletResponse response) throws IOException, UnsupportedEncodingException {
		response.setContentType("text/plain; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=sample.csv");
        recordService.transformToOutputStream(response.getOutputStream());
    	response.flushBuffer();
	}
}
