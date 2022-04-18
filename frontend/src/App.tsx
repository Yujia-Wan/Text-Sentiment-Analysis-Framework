import Handlebars from 'handlebars'
import { Data } from 'plotly.js'
import { Component } from 'react'
import Plot from 'react-plotly.js'
import './App.css'


var oldHref = "http://localhost:3000"


interface FrameworkState {
  instruction: String;
  template: HandlebarsTemplateDelegate<any>;
}

interface Props {
}

const data = '{"values":[2,1,2],"type":"pie","labels":["Positive",' +  ' "hi" '  + ' ,"Negative"]}';
let json: Data

class App extends Component<Props, FrameworkState> {
  constructor(props: Props) {
    super(props);
    this.state = {
      instruction: "This is instruction.",
      template: this.loadTemplate(),
    };
  }

  loadTemplate(): HandlebarsTemplateDelegate<any> {
    const src = document.getElementById("handlebars");
    return Handlebars.compile(src?.innerHTML, {});
  }

  getInstruction(p: any): String {
    return p["instruction"]
  }


  async submit(url: String) {
    const href = "generate?" + url.split("?")[1];
    const response = await fetch(href);
    const json1 = await response.json();
    const instruction = this.getInstruction(json1);
    const resultJson = this.setData(instruction);
    console.log(resultJson)
    this.setState({ instruction: instruction });
  }

  async setData(instruction: String) {
    const data = '{"values":[2,1,2],"type":"pie","labels":["Positive",' + '"'+ instruction  + '"' + ' ,"Negative"]}';
    json = JSON.parse(data)
  }
  


  async switch() {
    if (
      window.location.href.split("?")[0] === "http://localhost:3000/generate" &&
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
          json,
        ]}
        layout={ {width: 500, height: 400} }
      />
      </div>
    )
  };
};

export default App;
