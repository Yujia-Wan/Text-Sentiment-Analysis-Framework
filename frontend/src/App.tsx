import Handlebars from 'handlebars'
import { Component } from 'react'
import Plot from 'react-plotly.js'
import './App.css'

const data = '{"values":[2,1,2],"type":"pie","labels":["Positive","Neutral","Negative"]}';
let json = JSON.parse(data)

var oldHref = "http://localhost:3000/"
var dataPluginName: String
var dataPluginIndex: String
var displayPluginName: String

interface DataPluginCell {
  name: String;
  link: String;
}

interface DisplayPluginCell {
  name: String;
  link: String;
}

interface FrameworkState {
  instructions: String;
  // dataPluginCells: Array<DataPluginCell>;
  // displayPluginCells: Array<DisplayPluginCell>;
  template: HandlebarsTemplateDelegate<any>;
}

interface Props {
}

class App extends Component<Props, FrameworkState> {
  constructor(props: Props) {
    super(props);
    this.state = {
      instructions: "This is instructions.",
      template: this.loadTemplate(),
    };
  }

  loadTemplate(): HandlebarsTemplateDelegate<any> {
    const src = document.getElementById("handlebars");
    return Handlebars.compile(src?.innerHTML, {});
  }

  getDataPluginName(): String {
    return dataPluginName;
  }

  getDataPluginIndex(): String {
    return dataPluginIndex;
  }

  getDisplayPluginName(): String {
    return displayPluginName;
  }

  setDataPluginName(name: String): void {
    dataPluginName = name;
  }

  setDataPluginIndex(index: String): void {
    dataPluginIndex = index;
  }

  setDisplayPluginName(name: String): void {
    displayPluginName = name;
  }

  async submit(url: String) {
    const href = "?" + url.split("?")[1];
    const response = await fetch(href);
    const json = await response.json();
  }

  async switch() {
    // if (
    //   window.location.href.split("?")[0] === "http://localhost:3000/datapluginname" &&
    //   oldHref !== window.location.href
    // ) {
    //   // http://localhost:3000/datapluginname?x=twitter
    //   console.log(window.location.href);
    //   this.setDataPluginName(window.location.href.split("?")[1]);
    //   oldHref = window.location.href;
    // } else if (
    //   window.location.href.split("?")[0] === "http://localhost:3000/datapluginindex" &&
    //   oldHref !== window.location.href
    // ) {
    //   // http://localhost:3000/datapluginindex?y=username
    //   console.log(window.location.href);
    //   this.setDataPluginIndex(window.location.href.split("?")[1]);
    //   oldHref = window.location.href;
    // } else if (
    //   window.location.href.split("?")[0] === "http://localhost:3000/displaypluginname" &&
    //   oldHref !== window.location.href
    // ) {
    //   // http://localhost:3000/displaypluginname?z=piechart
    //   console.log(window.location.href);
    //   this.setDisplayPluginName(window.location.href.split("?")[1]);
    //   oldHref = window.location.href;
    // } else
    if (
      window.location.href === "http://localhost:3000/" &&
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
            __html: this.state.template({ instructions: "This is instructions." }),
          }}
        />
        <Plot
        data={[
          json,
        ]}
        layout={ {width: 320, height: 240} }
      />
      </div>
    )
  };
};

export default App;
