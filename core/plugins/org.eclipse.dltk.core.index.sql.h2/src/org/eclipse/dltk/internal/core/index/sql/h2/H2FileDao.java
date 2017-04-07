/*******************************************************************************
 * Copyright (c) 2009, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.dltk.internal.core.index.sql.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.index.sql.File;
import org.eclipse.dltk.core.index.sql.IFileDao;

/**
 * Element file data access object
 * 
 * @author michael
 * 
 */
public class H2FileDao implements IFileDao {

	private static final String Q_INSERT = "INSERT INTO FILES(PATH,TIMESTAMP,CONTAINER_ID) VALUES(?,?,?);"; //$NON-NLS-1$
	private static final String Q_SELECT = "SELECT * FROM FILES WHERE PATH=? AND CONTAINER_ID=?;"; //$NON-NLS-1$
	private static final String Q_SELECT_BY_CONTAINER_ID = "SELECT * FROM FILES WHERE CONTAINER_ID=?;"; //$NON-NLS-1$
	private static final String Q_SELECT_BY_ID = "SELECT * FROM FILES WHERE ID=?;"; //$NON-NLS-1$
	private static final String Q_DELETE = "DELETE FROM FILES WHERE PATH=? AND CONTAINER_ID=?;"; //$NON-NLS-1$
	private static final String Q_DELETE_BY_ID = "DELETE FROM FILES WHERE ID=?;"; //$NON-NLS-1$

	@Override
	public File insert(Connection connection, String path, long timestamp,
			int containerId) throws SQLException {

		try (PreparedStatement statement = connection.prepareStatement(Q_INSERT,
				Statement.RETURN_GENERATED_KEYS)) {
			int param = 0;
			statement.setString(++param, path);
			statement.setLong(++param, timestamp);
			statement.setInt(++param, containerId);
			statement.executeUpdate();

			try (ResultSet result = statement.getGeneratedKeys()) {
				result.next();
				File file = new File(result.getInt(1), path, timestamp,
						containerId);
				H2Cache.addFile(file);
				return file;
			}
		}
	}

	@Override
	public File select(Connection connection, String path, int containerId)
			throws SQLException {

		File file = H2Cache.selectFileByContainerIdAndPath(containerId, path);
		if (file == null) {
			try (PreparedStatement statement = connection
					.prepareStatement(Q_SELECT)) {
				int param = 0;
				statement.setString(++param, path);
				statement.setInt(++param, containerId);
				try (ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						file = new File(result.getInt(1), result.getString(2),
								result.getLong(3), result.getInt(4));

						H2Cache.addFile(file);
					}
				}
			}
		}
		return file;
	}

	@Override
	public File[] selectByContainerId(Connection connection, int containerId)
			throws SQLException {

		File[] files = H2Cache.selectFilesByContainerIdAsArray(containerId);
		if (files == null) {
			List<File> containerFiles = new LinkedList<>();

			try (PreparedStatement statement = connection
					.prepareStatement(Q_SELECT_BY_CONTAINER_ID)) {
				int param = 0;
				statement.setInt(++param, containerId);
				try (ResultSet result = statement.executeQuery()) {
					while (result.next()) {
						File file = new File(result.getInt(1),
								result.getString(2), result.getLong(3),
								result.getInt(4));

						containerFiles.add(file);
						H2Cache.addFile(file);
					}
				}
			}
			files = containerFiles.toArray(new File[containerFiles.size()]);
		}
		return files;
	}

	@Override
	public File selectById(Connection connection, int id) throws SQLException {

		File file = H2Cache.selectFileById(id);
		if (file == null) {
			try (PreparedStatement statement = connection
					.prepareStatement(Q_SELECT_BY_ID)) {
				int param = 0;
				statement.setInt(++param, id);
				try (ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						file = new File(result.getInt(1), result.getString(2),
								result.getLong(3), result.getInt(4));

						H2Cache.addFile(file);
					}
				}
			}
		}
		return file;
	}

	@Override
	public void delete(Connection connection, String path, int containerId)
			throws SQLException {

		try (PreparedStatement statement = connection
				.prepareStatement(Q_DELETE)) {
			int param = 0;
			statement.setString(++param, path);
			statement.setInt(++param, containerId);
			statement.executeUpdate();
		}

		H2Cache.deleteFileByContainerIdAndPath(containerId, path);
	}

	@Override
	public void deleteById(Connection connection, int id) throws SQLException {
		try (PreparedStatement statement = connection
				.prepareStatement(Q_DELETE_BY_ID)) {
			int param = 0;
			statement.setInt(++param, id);
			statement.executeUpdate();
		}

		H2Cache.deleteFileById(id);
	}
}
