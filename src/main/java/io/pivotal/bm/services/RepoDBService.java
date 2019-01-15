package io.pivotal.bm.services;

import io.pivotal.bm.domain.RepoDBRepository;
import io.pivotal.bm.models.RepoInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class RepoDBService implements RepoDBRepository {

    private JdbcTemplate jdbcTemplate;

    public RepoDBService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(RepoInfo repoInfo) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO git_repository (status, build_state, build_color, commit_difference) VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS
            );

            statement.setString(1, repoInfo.getStatus());
            statement.setString(2, repoInfo.getBuildState());
            statement.setString(3, repoInfo.getBuildColor());
            statement.setInt(4, repoInfo.getCountCommitDifference());

            return statement;
        }, generatedKeyHolder);
    }

    @Override
    public RepoInfo getRepoInfo() {
        ResultSetExtractor<RepoInfo> repoExtractor = rs -> {
            int countCommitDifference = rs.getInt("commit_difference");

            RepoInfo repoInfo = new RepoInfo(rs.getString("status"),
                    rs.getString("build_state"),
                    rs.getString("build_color"),
                    countCommitDifference);
            return repoInfo;
        };
        return jdbcTemplate.query("SELECT  * FROM repo_info", repoExtractor);
    }

    @Override
    public void update(RepoInfo repoInfo) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE git_repository SET status=?, build_state=?, build_color=?, commit_difference=? WHERE id=1)",
                    RETURN_GENERATED_KEYS
            );
            statement.setString(1, repoInfo.getStatus());
            statement.setString(2, repoInfo.getBuildState());
            statement.setString(3, repoInfo.getBuildColor());
            statement.setInt(4, repoInfo.getCountCommitDifference());
            return statement;
        }, generatedKeyHolder);
    }
}
