package vn.tritin.WebHoatHinh.service;

import java.util.List;

import org.springframework.ai.document.Document;

import vn.tritin.WebHoatHinh.model.VectorStoreDTO;

public interface VectorStoreService {
	public void insertData(VectorStoreDTO data);

	public void updateData(VectorStoreDTO data);

	public void deleteData(String id);

	public List<Document> getDataByDescription(String description);
}
