package ProjetoLojaDeCarro.Projeto;

import java.sql.*;
import java.util.Scanner;

public class LojaDeCarros {

    public void adicionarCarro(Carro carro) {
        String sql = "INSERT INTO carros (marca, modelo, ano, preco) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBancoDadosDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getModelo());
            stmt.setInt(3, carro.getAno());
            stmt.setDouble(4, carro.getPreco());
            stmt.executeUpdate();
            System.out.println("Carro adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar carro: " + e.getMessage());
        }
    }

    public void listarCarros() {
        String sql = "SELECT * FROM carros";
        try (Connection conn = ConexaoBancoDadosDAO.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Carro carro = new Carro(
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getInt("ano"),
                    rs.getDouble("preco")
                );
                System.out.println(carro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar carros: " + e.getMessage());
        }
    }

    public void atualizarCarro(int id, Carro carroAtualizado) {
        String sql = "UPDATE carros SET marca = ?, modelo = ?, ano = ?, preco = ? WHERE id = ?";
        try (Connection conn = ConexaoBancoDadosDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, carroAtualizado.getMarca());
            stmt.setString(2, carroAtualizado.getModelo());
            stmt.setInt(3, carroAtualizado.getAno());
            stmt.setDouble(4, carroAtualizado.getPreco());
            stmt.setInt(5, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Carro atualizado com sucesso!");
            } else {
                System.out.println("Carro não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar carro: " + e.getMessage());
        }
    }

    public void removerCarro(int id) {
        String sql = "DELETE FROM carros WHERE id = ?";
        try (Connection conn = ConexaoBancoDadosDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Carro removido com sucesso!");
            } else {
                System.out.println("Carro não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover carro: " + e.getMessage());
        }
    }

    public void buscarCarrosPorMarca(String marca) {
        String sql = "SELECT * FROM carros WHERE marca = ?";
        try (Connection conn = ConexaoBancoDadosDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, marca);
            try (ResultSet rs = stmt.executeQuery()) {
                boolean encontrado = false;
                while (rs.next()) {
                    Carro carro = new Carro(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getDouble("preco")
                    );
                    System.out.println(carro);
                    encontrado = true;
                }
                if (!encontrado) {
                    System.out.println("Nenhum carro encontrado para a marca " + marca);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar carros: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        LojaDeCarros loja = new LojaDeCarros();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLoja de Carros - Menu:");
            System.out.println("1. Adicionar carro");
            System.out.println("2. Listar carros");
            System.out.println("3. Atualizar carro");
            System.out.println("4. Remover carro");
            System.out.println("5. Buscar carros por marca");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.print("Marca: ");
                    String marca = scanner.nextLine();
                    System.out.print("Modelo: ");
                    String modelo = scanner.nextLine();
                    System.out.print("Ano: ");
                    int ano = scanner.nextInt();
                    System.out.print("Preço: ");
                    double preco = scanner.nextDouble();
                    scanner.nextLine(); 
                    Carro carro = new Carro(marca, modelo, ano, preco);
                    loja.adicionarCarro(carro);
                    break;
                case 2:
                    loja.listarCarros();
                    break;
                case 3:
                    System.out.print("ID do carro a ser atualizado: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Nova Marca: ");
                    String novaMarca = scanner.nextLine();
                    System.out.print("Novo Modelo: ");
                    String novoModelo = scanner.nextLine();
                    System.out.print("Novo Ano: ");
                    int novoAno = scanner.nextInt();
                    System.out.print("Novo Preço: ");
                    double novoPreco = scanner.nextDouble();
                    scanner.nextLine(); 
                    Carro carroAtualizado = new Carro(novaMarca, novoModelo, novoAno, novoPreco);
                    loja.atualizarCarro(idAtualizar, carroAtualizado);
                    break;
                case 4:
                    System.out.print("ID do carro a ser removido: ");
                    int idRemover = scanner.nextInt();
                    scanner.nextLine(); 
                    loja.removerCarro(idRemover);
                    break;
                case 5:
                    System.out.print("Marca para buscar: ");
                    String marcaBusca = scanner.nextLine();
                    loja.buscarCarrosPorMarca(marcaBusca);
                    break;
                case 6:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
