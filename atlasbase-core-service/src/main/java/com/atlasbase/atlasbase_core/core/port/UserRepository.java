package com.atlasbase.atlasbase_core.core.port;

import com.atlasbase.atlasbase_core.core.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

	Optional<User> findByUserName(String userName);

	Optional<User> findByEmail(String email);

	void save(User user);

	void deleteAll();

	void saveAll(List<User> users);

}