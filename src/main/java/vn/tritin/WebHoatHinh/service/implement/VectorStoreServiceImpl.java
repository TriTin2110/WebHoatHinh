package vn.tritin.WebHoatHinh.service.implement;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.tritin.WebHoatHinh.model.VectorStoreDTO;
import vn.tritin.WebHoatHinh.service.VectorStoreService;

@Service
public class VectorStoreServiceImpl implements VectorStoreService {

	@Autowired
	private VectorStore vectorStore;
	private final int LIMIT_RESULT = 3;
	private final float MINIUM_SCORE = 0.85f;

	@Override
	public void insertData(VectorStoreDTO data) {
		// TODO Auto-generated method stub
		Document document = new Document(data.toString(), Map.of("id", data.getId()));
		List<Document> documentSplitted = getDocumentSplitted(document);
		vectorStore.add(documentSplitted);
	}

	@Override
	public void updateData(VectorStoreDTO data) {
		Document document = new Document(data.getDescription(), Map.of("id", data.getId()));
		List<Document> documentSplitted = getDocumentSplitted(document);

		Consumer<VectorStoreDTO> deleteData = o -> vectorStore
				.delete(new FilterExpressionBuilder().eq("id", o.getId()).build());
		Consumer<VectorStoreDTO> insertData = o -> vectorStore.add(documentSplitted);
		Consumer<VectorStoreDTO> combine = deleteData.andThen(insertData);
		combine.accept(data); // Combine 2 action with object VectorStoreDTO
	}

	@Override
	public void deleteData(String id) {
		vectorStore.delete(new FilterExpressionBuilder().eq("id", id).build());
	}

	private List<Document> getDocumentSplitted(Document document) {
		TextSplitter splitter = new TokenTextSplitter();
		List<Document> documentSplitted = splitter.split(document);
		return documentSplitted;
	}

	@Override
	public List<Document> getDataByDescription(String description) {
		// TODO Auto-generated method stub
		List<Document> documents = vectorStore
				.similaritySearch(SearchRequest.builder().query(description).similarityThreshold(MINIUM_SCORE).build());
		return documents;
	}
}
