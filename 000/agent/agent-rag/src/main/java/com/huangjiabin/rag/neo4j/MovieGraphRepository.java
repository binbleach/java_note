package com.huangjiabin.rag.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//电影和导演关系
@Repository
public interface MovieGraphRepository extends Neo4jRepository<Movie, String> {
    @Query("""
            MATCH (m:Movie {title: $title}) <-[:DIRECTED]- (d:Director) -[:DIRECTED]-> (other:Movie)
            WHERE other.title <> $title
            RETURN d.name AS director, collect(other.title + ' (' + other.year + ')') AS otherMovies
            """)
    List<DirectorMoviesDto> findOtherMoviesBySameDirector(String title);
}