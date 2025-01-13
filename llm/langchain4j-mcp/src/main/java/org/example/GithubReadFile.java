package org.example;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;

import java.util.List;
import java.util.Map;

public class GithubReadFile {

    public interface Bot {
        String chat(String prompt);
    }

    public static void main(String[] args) throws Exception {
        if (System.getenv("OPENAI_API_KEY") == null) {
            throw new RuntimeException("OPENAI_API_KEY environment variable is not set");
        }
        if (System.getenv("GITHUB_PERSONAL_ACCESS_TOKEN") == null) {
            throw new RuntimeException("GITHUB_PERSONAL_ACCESS_TOKEN environment variable is not set");
        }

        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName("gpt-4o-mini")
//                .logRequests(true)
//                .logResponses(true)
                .build();
        McpTransport transport = new StdioMcpTransport.Builder()
                .command(List.of("/usr/bin/npm", "exec",
                        "@modelcontextprotocol/server-github"
                ))
                .environment(Map.of("GITHUB_PERSONAL_ACCESS_TOKEN",
                        System.getenv("GITHUB_PERSONAL_ACCESS_TOKEN")))
                .logEvents(true)
                .build();
        McpClient mcpClient = new DefaultMcpClient.Builder()
                .transport(transport)
                .build();

        ToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(List.of(mcpClient))
                .build();

        Bot bot = AiServices.builder(Bot.class)
                .chatLanguageModel(model)
                .toolProvider(toolProvider)
                .build();
        try {
//            String response = bot.chat("Create a new issue in the repository 'jmartisk/mock-artifacts' with title: 'Test issue' and body: 'This is a test issue'");
            String response = bot.chat("Read the file 'README.md' in the repository 'jmartisk/mock-artifacts'");
            System.out.println(response);
        } finally {
            mcpClient.close();
        }
    }
}
