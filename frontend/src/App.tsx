import Handlebars from "handlebars"
import { Component } from 'react'
import { threadId } from "worker_threads"
import './App.css'

var oldHref = "http://localhost:3000"
var dataPluginName: String
var dataPluginIndex: String
var displayPluginName: String

interface FrameworkState {

}

interface Props {
}

class App extends Component<Props, FrameworkState> {
  constructor(props: Props) {
    super(props);
    this.state = {
      template: this.loadTemplate,
      
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

  async generate() {
    const href = "generate?" + this.getDataPluginName + "&" + this.getDataPluginIndex + "&" + this.getDisplayPluginName;
    const response = await fetch(href);
    const json = await response.json();

  }

  async switch() {
    if (
      window.location.href.split("?")[0] === "http://localhost:3000/datapluginname" &&
      oldHref !== window.location.href
    ) {
      // http://localhost:3000/datapluginname?x=twitter
      console.log(window.location.href);
      this.setDataPluginName(window.location.href.split("?")[1]);
      oldHref = window.location.href;
    } else if (
      window.location.href.split("?")[0] === "http://localhost:3000/datapluginindex" &&
      oldHref !== window.location.href
    ) {
      // http://localhost:3000/datapluginindex?y=username
      console.log(window.location.href);
      this.setDataPluginIndex(window.location.href.split("?")[1]);
      oldHref = window.location.href;
    } else if (
      window.location.href.split("?")[0] === "http://localhost:3000/displaypluginname" &&
      oldHref !== window.location.href
    ) {
      // http://localhost:3000/displaypluginname?z=piechart
      console.log(window.location.href);
      this.setDisplayPluginName(window.location.href.split("?")[1]);
      oldHref = window.location.href;
    } else if (
      window.location.href === "http://localhost:3000/generate" &&
      oldHref !== window.location.href
    ) {
      this.generate();
      oldHref = window.location.href;
    }
  }

  render() {
    this.switch()
    return (
      <div className="App">
        <div
          dangerouslySetInnerHTML={{
            __html: this.state.template({}),
          }}
        />
      </div>
    )
  };
};

export default App;
