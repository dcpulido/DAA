package es.uvigo.esei.daa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.esei.daa.entities.Pet;

public class PetDAO extends DAO {
	private final static Logger LOG = Logger.getLogger(PetDAO.class.getName());


	public Pet get(int id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM pet WHERE id=?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				
				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return rowToEntity(result);
					} else {
						throw new IllegalArgumentException("Invalid id");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting a pet", e);
			throw new DAOException(e);
		}
	}

	public List<Pet> list() throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM pet";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				try (final ResultSet result = statement.executeQuery()) {
					final List<Pet> pet = new LinkedList<>();
					
					while (result.next()) {
						pet.add(rowToEntity(result));
					}
					
					return pet;
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing pet", e);
			throw new DAOException(e);
		}
	}

	public Pet add(String name, String tipe, int idPeople )
	throws DAOException, IllegalArgumentException {
		if (name == null || tipe == null) {
			throw new IllegalArgumentException("name and tipe can't be null");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "INSERT INTO pet VALUES(null, ?, ?, ?)";
			
			try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, name);
				statement.setString(2, tipe);
				statement.setInt(3, idPeople);
				
				if (statement.executeUpdate() == 1) {
					try (ResultSet resultKeys = statement.getGeneratedKeys()) {
						if (resultKeys.next()) {
							return new Pet(resultKeys.getInt(1), name, tipe, idPeople);
						} else {
							LOG.log(Level.SEVERE, "Error retrieving inserted id");
							throw new SQLException("Error retrieving inserted id");
						}
					}
				} else {
					LOG.log(Level.SEVERE, "Error inserting value");
					throw new SQLException("Error inserting value");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error adding a pet", e);
			throw new DAOException(e);
		}
	}

	public void modify(Pet pet)
	throws DAOException, IllegalArgumentException {
		if (pet == null) {
			throw new IllegalArgumentException("pet can't be null");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "UPDATE people SET name=?, tipe=? WHERE id=?";
			
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, pet.getName());
				statement.setString(2, pet.getTipe());
				statement.setInt(3, pet.getId());
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("name and tipe can't be null");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error modifying a pet", e);
			throw new DAOException();
		}
	}

	public void delete(int id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "DELETE FROM pet WHERE id=?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("Invalid id");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error deleting a pet", e);
			throw new DAOException(e);
		}
	}
	
	private Pet rowToEntity(ResultSet row) throws SQLException {
		return new Pet(
			row.getInt("id"),
			row.getString("name"),
			row.getString("tipe"),
			row.getInt("idPeople")
		);
	}

}