import os
from openai import OpenAI

# 注意: 不同地域的base_url不通用（下方示例使用北京地域的 base_url）
# - 华北2（北京）: https://dashscope.aliyuncs.com/compatible-mode/v1
# - 美国（弗吉尼亚）: https://dashscope-us.aliyuncs.com/compatible-mode/v1
# - 新加坡: https://{WorkspaceId}.ap-southeast-1.maas.aliyuncs.com/compatible-mode/v1，请将WorkspaceId替换为业务空间ID
# - 德国（法兰克福）: https://{WorkspaceId}.eu-central-1.maas.aliyuncs.com/compatible-mode/v1，请将WorkspaceId替换为业务空间ID
client = OpenAI(
    api_key="sk-1e1a5e1570454cff9c090e6661ba219a",
    base_url="https://dashscope.aliyuncs.com/compatible-mode/v1",
)
completion = client.chat.completions.create(
    model="qwen3.6-plus",
    messages=[{'role': 'user', 'content': '你是谁，称呼我为憋嘴？'}]
)
print(completion.choices[0].message.content)