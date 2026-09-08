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
from llama_index.llms.dashscope import DashScope
from llama_index.embeddings.dashscope import DashScopeEmbedding, DashScopeTextEmbeddingModels

# 1. 加载环境变量，移除硬编码密钥
load_dotenv()
DASHSCOPE_API_KEY = os.getenv("DASHSCOPE_API_KEY")

# 必填密钥校验
if not DASHSCOPE_API_KEY:
    raise ValueError("错误：请在项目根目录 .env 文件配置 DASHSCOPE_API_KEY")

# 读取可配置参数
LLM_MODEL = os.getenv("LLM_MODEL", "qwen-turbo")
CHUNK_SIZE = int(os.getenv("CHUNK_SIZE", 800))
CHUNK_OVERLAP = int(os.getenv("CHUNK_OVERLAP", 150))
TOP_K = int(os.getenv("TOP_K", 3))
REQUEST_TIMEOUT = int(os.getenv("REQUEST_TIMEOUT", 120))

# ===================== 全局配置 Settings =====================
# 嵌入模型：显式传入api_key，不依赖全局环境变量
Settings.embed_model = DashScopeEmbedding(
    model_name=DashScopeTextEmbeddingModels.TEXT_EMBEDDING_V2,
    api_key=DASHSCOPE_API_KEY
)

# LLM大模型：改用字符串，兼容所有通义系列模型
Settings.llm = DashScope(
    model_name=LLM_MODEL,
    api_key=DASHSCOPE_API_KEY,
    temperature=0.0,
    request_timeout=REQUEST_TIMEOUT
)

# 文本分块参数
Settings.chunk_size = CHUNK_SIZE
Settings.chunk_overlap = CHUNK_OVERLAP

# 索引持久化目录
STORAGE_DIR = "./dashscope_rag_store"

# ===================== 构建/加载向量索引 =====================
def build_rag_index(doc_folder: str = "./docs") -> VectorStoreIndex:
    # 校验文档文件夹是否存在
    if not os.path.isdir(doc_folder):
        raise FileNotFoundError(f"文档目录不存在：{doc_folder}，请创建文件夹并放入pdf/txt/md文档")

    if os.path.exists(STORAGE_DIR):
        # 读取已有索引
        storage_ctx = StorageContext.from_defaults(persist_dir=STORAGE_DIR)
        index = load_index_from_storage(storage_ctx)
        print("✅ 加载本地已存在向量索引")
    else:
        # 读取文件夹下所有pdf/txt/md文件
        documents = SimpleDirectoryReader(input_dir=doc_folder).load_data()
        if len(documents) == 0:
            raise RuntimeError(f"{doc_folder} 文件夹下未读取到任何文档，请检查文件")
        print(f"✅ 读取文档数量: {len(documents)}")
        # 构建向量索引，自动读取全局Settings向量模型
        index = VectorStoreIndex.from_documents(documents)
        # 持久化保存
        index.storage_context.persist(persist_dir=STORAGE_DIR)
        print("✅ 新建向量索引并持久化完成")
    return index

# ===================== 自定义问答Prompt（严格限定文档内容） =====================
qa_prompt = PromptTemplate("""
你是企业内部文档问答助手，只能依据下面提供的上下文内容回答用户问题，禁止编造、脑补不存在信息。
如果上下文完全没有相关内容，直接回复：【文档中未检索到相关信息】，不要额外解释。

上下文片段：
{context_str}

用户问题：{query_str}
请简洁准确回答：
""")

# ===================== 主入口 =====================
if __name__ == "__main__":
    try:
        # 1. 构建索引，文档放入 ./docs 文件夹
        rag_index = build_rag_index(doc_folder="./docs")

        # 2. 创建查询引擎
        query_engine = rag_index.as_query_engine(
            similarity_top_k=TOP_K,
            text_qa_template=qa_prompt,
            response_mode="compact"
        )

        # 3. 提问测试
        user_query = "文档中介绍的核心流程是什么？"
        resp = query_engine.query(user_query)

        # 输出回答
        print("=" * 60)
        print("AI回答：")
        print(resp)
        print("=" * 60)

        # 输出检索到的原文来源
        print("引用检索片段：")
        for idx, node in enumerate(resp.source_nodes):
            print(f"\n【片段{idx+1} 相似度分数:{node.score:.4f}】")
            print(node.text[:350] + "...")
    except Exception as e:
        print(f"程序运行异常：{str(e)}")