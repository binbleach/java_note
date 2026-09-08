import os
from dotenv import load_dotenv
from llama_index.core import (
    SimpleDirectoryReader,
    VectorStoreIndex,
    Settings,
    StorageContext,
    load_index_from_storage,
    PromptTemplate
)
# DashScope LLM & Embedding
from llama_index.llms.dashscope import DashScope, DashScopeGenerationModels
from llama_index.embeddings.dashscope import DashScopeEmbedding, DashScopeTextEmbeddingModels

# 加载密钥
load_dotenv()
# 填入你的阿里云dashscope api key
DASHSCOPE_API_KEY = os.getenv("DASHSCOPE_API_KEY")
REQUEST_TIMEOUT = int(os.getenv("REQUEST_TIMEOUT", 120))
print("api_key：", DASHSCOPE_API_KEY)
print("timeout：",REQUEST_TIMEOUT)

# ===================== 全局配置 Settings =====================
# 1. 嵌入模型：通义文本向量化
embed_model = DashScopeEmbedding(
    model_name=DashScopeTextEmbeddingModels.TEXT_EMBEDDING_V2,   #向量化固定模型
    api_key=DASHSCOPE_API_KEY
)

# 2. LLM大模型：通义千问
llm = DashScope(
    model_name="deepseek-v4-flash",
    api_key=DASHSCOPE_API_KEY,
    temperature=0.0,
    request_timeout=REQUEST_TIMEOUT
)

# 3、加载文档
docs = SimpleDirectoryReader("data").load_data()

# 4、构建索引
index = VectorStoreIndex.from_documents(docs,embed_model=embed_model)

 # 5、创建查询引擎
query_engine = index.as_query_engine(
    llm=llm ,
    similarity_top_k=3, #默认2
    response_mode="compact" #默认compact
)
# 6、提问
resp = query_engine.query("请总结这份文档的主要内容。")
print("回答：", resp)
print("检索片段数量：", len(resp.source_nodes))
node = resp.source_nodes[0]
print("片段相似度：", node.score)
print("片段完整文本：\n", node.text)