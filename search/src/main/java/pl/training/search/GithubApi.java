package pl.training.search;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubApi {

    @GET("/search/repositories")
    Observable<QueryResultDto> get(@Query("q") String query);

}
