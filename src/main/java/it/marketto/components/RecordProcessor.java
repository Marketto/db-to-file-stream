package it.marketto.components;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Stream;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import it.marketto.repositories.RecordRepository;

@Component
public class RecordProcessor {
	private final RecordRepository recordRepository;
	
	public RecordProcessor(RecordRepository recordRepository) {
		this.recordRepository = recordRepository;
	}
	
	@Transactional(readOnly = true)
	public void processRecord(OutputStream outputStream) {
		byte[] utf8Bom = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
		try {
			outputStream.write(utf8Bom);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Stream<Object[]> recordStream = recordRepository.getAll();
		
		recordStream.sequential().forEach(record -> {
			String[] dataArray = Arrays.stream(record[1].toString().split(","))
					.map(field -> {
						byte[] rawDecodedField = Base64.getDecoder().decode(field);
						String decodedField = new String(rawDecodedField);
						String csvUnescapedField = StringEscapeUtils.escapeCsv(decodedField);
						return csvUnescapedField;
					})
					.toArray(String[]::new);
			String csvRow = StringUtils.arrayToDelimitedString(dataArray, ";");
        	byte[] byteArrayRow = null;
			try {
				byteArrayRow = csvRow.getBytes("UTF8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			try {
				outputStream.write(byteArrayRow);
				outputStream.write('\n');
				outputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
