from fastapi import FastAPI
from dotenv import load_dotenv
import os
from langchain_openai import ChatOpenAI
from langchain.prompts import PromptTemplate
from langchain.chains import LLMChain

# 加载.env密钥配置
load_dotenv()

# 初始化接口服务
app = FastAPI(title="LangChain翻译系统")

# 初始化大模型
llm = ChatOpenAI(
    model="qwen3.7-plus",
    api_key=os.getenv("OPENAI_API_KEY"),
    base_url=os.getenv("OPENAI_BASE_URL")
)

# 翻译提示词模板
translate_template = PromptTemplate(
    input_variables=["source_text", "source_lang", "target_lang"],
    template="""
你是专业翻译助手，只输出翻译结果，不要多余文字。
源语言：{source_lang}
目标语言：{target_lang}
待翻译文本：{source_text}
"""
)

# 构建翻译链路
translate_chain = LLMChain(llm=llm, prompt=translate_template)

# 翻译接口
@app.get("/translate")
def translate_api(text: str, source: str = "中文", target: str = "英文"):
    res = translate_chain.run(source_text=text, source_lang=source, target_lang=target)
    return {
        "原文": text,
        "源语言": source,
        "目标语言": target,
        "翻译结果": res
    }

# 标准main入口函数
def main():
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)

# 程序启动入口
if __name__ == "__main__":
    main()