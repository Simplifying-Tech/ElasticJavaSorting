import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

public class ElasticjavaSorting {

	public static void main(String[] args) {
		
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));
		
		SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("coachingclassesidx");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //searchSourceBuilder.sort(SortBuilders.fieldSort("instructor.keyword").order(SortOrder.DESC));
        //searchSourceBuilder.sort(SortBuilders.fieldSort("fees").order(SortOrder.DESC));
        searchSourceBuilder.sort(SortBuilders.fieldSort("start_date").order(SortOrder.DESC));



        searchRequest.source(searchSourceBuilder);
        
        Map<String, Object> map=null;
        
        try {
            SearchResponse searchResponse = null;
            searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    map = hit.getSourceAsMap();
                      System.out.println("Elastic Data:"+Arrays.toString(map.entrySet().toArray()));
                          
                      
                }
            }
             
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
