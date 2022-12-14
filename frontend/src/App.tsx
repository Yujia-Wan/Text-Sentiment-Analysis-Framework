import Handlebars from 'handlebars'
import { Data } from 'plotly.js'
import { Component } from 'react'
import Plot from 'react-plotly.js'
import './App.css'

var oldHref = "http://localhost:3000"
var data: Data

interface FrameworkState {
  instruction: String;
  template: HandlebarsTemplateDelegate<any>;
}

interface Props {
}

class App extends Component<Props, FrameworkState> {
  constructor(props: Props) {
    super(props);
    this.state = {
      instruction: "Customize your config and start sentiment analysis!",
      template: this.loadTemplate(),
    };
  }

  loadTemplate(): HandlebarsTemplateDelegate<any> {
    const src = document.getElementById("handlebars");
    return Handlebars.compile(src?.innerHTML, {});
  }

  getInstruction(p: any): String {
    if (p["instruction"] === "") {
      return "Customize your config and start sentiment analysis!";
    }
    return p["instruction"];
  }

  getData(p: any): String {
    return p["data"];
  }

  async setData(d: any) {
    data = d;
  }

  async submit(url: String) {
    const href = "submit?" + url.split("?")[1];
    const response = await fetch(href);
    const json = await response.json();

    const instr = this.getInstruction(json);
    this.setData(this.getData(json));
    this.setState({ instruction: instr });
  }

  async switch() {
    if (
      window.location.href.split("?")[0] === "http://localhost:3000/submit" &&
      oldHref !== window.location.href
    ) {
      this.submit(window.location.href);
      oldHref = window.location.href;
    }
  }

  render() {
    this.switch()
    return (
      <div className="App">
        <div
          dangerouslySetInnerHTML={{
            __html: this.state.template({ instruction: this.state.instruction }),
          }}
        />
        <Plot
          data={[
            data,
          ]}
          layout={{width: 800, height: 600}}
        />
      </div>
    )
  };
};

export default App;
