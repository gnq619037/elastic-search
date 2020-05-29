package com.gnq.elastic.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gnq.elastic.bean.User;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: guonanqing
 * @desc:
 * @date: 2020/5/27
 */
@Component
public class EsUtil {
    @Autowired
    private RestHighLevelClient highLevelClient;
    private int indexid = 0;

    public void createIndex(String index, String id, String type, String jsonStr) throws IOException{
        GetRequest getRequest = new GetRequest(index, type, id);
        String[] includes = new String[]{"name", "address"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        getRequest.fetchSourceContext(fetchSourceContext);
        getRequest.storedFields("name", "address");
//        boolean isExits = highLevelClient.exists(getRequest, RequestOptions.DEFAULT);
//        System.out.println("isExits:" + isExits);
        GetResponse getResponse = highLevelClient.get(getRequest, RequestOptions.DEFAULT);
        if(getResponse.isExists()) {
            String sourceStr = getResponse.getSourceAsString();
            System.out.println(sourceStr);
            return;
        }

        IndexRequest indexRequest = new IndexRequest(index, type);
        indexRequest.id(id);
        indexRequest.source(jsonStr, XContentType.JSON).opType(DocWriteRequest.OpType.CREATE);

        IndexResponse indexResponse = highLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSONString(indexResponse));

        String indexRsp = indexResponse.getIndex();
        String idRsp = indexResponse.getId();
        System.out.println("->rsp:"+indexRsp+",id:"+idRsp);
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("...create");
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("...update");
        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if(shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("...total");
        }
        if(shardInfo.getFailed() > 0) {
            for(ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.println(reason);
            }
        }
    }

    public void delete(String index, String type, String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index,type, id);
        DeleteResponse deleteResponse = highLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSONString(deleteResponse));
    }

    public void update(String index, String type, String id) throws IOException {

    }

    public void search(String index){
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
    }
}
