package pl.training.search;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Predicate;
import lombok.extern.java.Log;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;

import static java.lang.Runtime.getRuntime;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BASIC;

@Log
public class Application {

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final GithubApi githubApi = new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(BASIC))
                    .build()
            )
            .build()
            .create(GithubApi.class);


    private Observable<List<String>> getRepositories(String query) {
        return githubApi.get(query)
                .map(QueryResultDto::getItems)
                .flatMap(Observable::fromIterable)
                .map(RepositoryDto::getName)
                .map(String::toLowerCase)
                .sorted()
                .distinct()
                .filter(byLength(3))
                .toList()
                .toObservable();
    }

    private void start() {
        getRuntime().addShutdownHook(new Thread(disposables::dispose));

        var stream= ObservableInputStream.from(System.in)
                .map(String::toLowerCase)
                .flatMap(this::getRepositories);


        disposables.add(stream.subscribe(result -> log.info(result.toString()), exception -> log.warning(exception.toString())));
    }

    private Predicate<String> byLength(int length) {
        return name -> name.length() > length;
    }

    public static void main(String[] args) {
        new Application().start();
    }

}
