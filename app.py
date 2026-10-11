from flask import Flask, render_template, request

app = Flask(__name__)

# Lista global que vai salvar o histórico de cálculos na memória do servidor
historico_calculos = []

@app.route("/", methods=["GET", "POST"])
def index():
    salario_liquido = None
    erro = None

    if request.method == "POST":
        try:
            salario_bruto = float(request.form["salario_bruto"])
            dependentes = int(request.form["dependentes"])
            
            if salario_bruto < 0 or dependentes < 0:
                erro = "Erro: O salário e o número de dependentes não podem ser negativos."
            else:
                inss = salario_bruto * 0.08
                ir = salario_bruto * 0.15 if salario_bruto > 2500.00 else 0.0
                bonus_dependentes = dependentes * 200.00
                
                salario_liquido = salario_bruto - inss - ir + bonus_dependentes

                # Salva os dados do cálculo atual em um dicionário e adiciona na lista
                calculo_atual = {
                    "bruto": salario_bruto,
                    "dependentes": dependentes,
                    "liquido": salario_liquido
                }
                historico_calculos.append(calculo_atual)

        except ValueError:
            erro = "Erro: Insira valores numéricos válidos."

    # Passamos a lista 'historico_calculos' para dentro do HTML
    return render_template(
        "form.html",
        salario_liquido=salario_liquido,
        erro=erro,
        historico=historico_calculos
    )

if __name__ == "__main__":
    app.run(debug=True)
